package ru.niceaska.wikitest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.niceaska.wikitest.data.exceptions.RequestLocationException
import ru.niceaska.wikitest.domain.interactors.WikiGetPagesInteractor
import ru.niceaska.wikitest.presentation.converters.UIWidgetPlacesConverter
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

class MainViewModel(
    private val getPagesInteractor: WikiGetPagesInteractor,
    private val converter: UIWidgetPlacesConverter
) : ViewModel() {

    private val _placesList: MutableLiveData<List<WikiGeoPagePresentation>> = MutableLiveData()
    val placesList: LiveData<List<WikiGeoPagePresentation>>
        get() = _placesList


    fun fetchData() {
        getListOfPlaces(false)
    }

    private fun getListOfPlaces(force: Boolean) {
        getPagesInteractor.getPagesList(force)
            .map { converter.convert(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .subscribe({
                _placesList.value = it
            }, { thr -> if (thr is RequestLocationException) getListOfPlaces(force) }
            )
    }
}