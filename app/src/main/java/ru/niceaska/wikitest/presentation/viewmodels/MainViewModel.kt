package ru.niceaska.wikitest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.niceaska.wikitest.data.exceptions.RequestLocationException
import ru.niceaska.wikitest.domain.interactors.WikiGetImagesTitlesInteractor
import ru.niceaska.wikitest.domain.interactors.WikiGetPagesInteractor
import ru.niceaska.wikitest.presentation.converters.UIWidgetPlacesConverter
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation
import ru.niceaska.wikitest.presentation.models.WikiResult


/**
 * Вью модель для основной активити приложения
 */
class MainViewModel(
    private val getPagesInteractor: WikiGetPagesInteractor,
    private val getImagesTitlesInteractor: WikiGetImagesTitlesInteractor,
    private val converter: UIWidgetPlacesConverter
) : ViewModel() {

    /**
     * [LiveData] для предоставления результата [Result] списка статей википедии [WikiGeoPagePresentation]
     */
    private val _resultListPlacesLiveData: MutableLiveData<WikiResult<List<WikiGeoPagePresentation>, Throwable>> =
        MutableLiveData()
    val resultLiveData: LiveData<WikiResult<List<WikiGeoPagePresentation>, Throwable>>
        get() = _resultListPlacesLiveData

    /**
     * [LiveData] для предоставления результата [Result] списка заголовоков картинок
     */
    private val _resultImagesTitlesLiveData: MutableLiveData<WikiResult<List<String>, Throwable>> =
        MutableLiveData()
    val resultImagesTitlesLiveData: LiveData<WikiResult<List<String>, Throwable>>
        get() = _resultImagesTitlesLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Запросить геолокацию и получить список статей
     *
     * @param force    определяет будет ли геолокация запрошена принудительно,
     *                 при false берет последнее известное значение
     */
    fun fetchData(force: Boolean) {
        getListOfPlaces(force)
    }

    /**
     * Получить список статей по [id]
     */
    fun getArticleImagesTitles(id: Long) {
        getImagesTitlesInteractor.getArticleImageTitles(id)
            .doOnSubscribe { _resultImagesTitlesLiveData.postValue(WikiResult.Loading(true)) }
            .doFinally { _resultImagesTitlesLiveData.postValue(WikiResult.Loading(false)) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .subscribe({
                _resultImagesTitlesLiveData.value = WikiResult.Success(it)
            }, { thr -> _resultImagesTitlesLiveData.value = WikiResult.Error(thr) }
            ).addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun getListOfPlaces(force: Boolean) {
        getPagesInteractor.getPagesList(force)
            .map { converter.convert(it) }
            .doOnSubscribe { _resultListPlacesLiveData.postValue(WikiResult.Loading(true)) }
            .doFinally { _resultListPlacesLiveData.postValue(WikiResult.Loading(false)) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .subscribe({
                _resultListPlacesLiveData.value = WikiResult.Success(it)
            }, { thr ->
                if (thr is RequestLocationException) {
                    getListOfPlaces(force = true)
                } else {
                    _resultListPlacesLiveData.value = WikiResult.Error(thr)
                }
            }
            ).addTo(compositeDisposable)

    }
}