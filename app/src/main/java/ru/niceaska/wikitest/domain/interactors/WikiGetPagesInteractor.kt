package ru.niceaska.wikitest.domain.interactors

import io.reactivex.Single
import ru.niceaska.wikitest.data.converters.WikiPlacesConverter
import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain
import ru.niceaska.wikitest.domain.repository.DataRepository

class WikiGetPagesInteractor(
    private val repository: DataRepository,
    private val converter: WikiPlacesConverter
) {

    fun getPagesList(force: Boolean): Single<List<WikiGeoPageDomain>> =
        repository.getWikiPlacesList(force).map {
            converter.convert(it)
        }
}