package ru.niceaska.wikitest.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.niceaska.wikitest.domain.interactors.WikiGetPagesInteractor
import ru.niceaska.wikitest.presentation.converters.UIWidgetPlacesConverter
import ru.niceaska.wikitest.presentation.viewmodels.MainViewModel

class ViewModelFactory(
    private val getPagesInteractor: WikiGetPagesInteractor,
    private val converter: UIWidgetPlacesConverter
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getPagesInteractor = getPagesInteractor, converter = converter) as T
    }

}