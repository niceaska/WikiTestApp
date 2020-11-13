package ru.niceaska.wikitest.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.niceaska.wikitest.domain.interactors.WikiGetImagesTitlesInteractor
import ru.niceaska.wikitest.domain.interactors.WikiGetPagesInteractor
import ru.niceaska.wikitest.presentation.converters.UIWidgetPlacesConverter

class ViewModelFactory(
    private val getPagesInteractor: WikiGetPagesInteractor,
    private val wikiGetImagesTitlesInteractor: WikiGetImagesTitlesInteractor,
    private val converter: UIWidgetPlacesConverter
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            getPagesInteractor = getPagesInteractor,
            getImagesTitlesInteractor = wikiGetImagesTitlesInteractor,
            converter = converter
        ) as T
    }
}