package ru.niceaska.wikitest.data.converters

import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper
import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain

class WikiPlacesConverter {

    fun convert(data: WikiFePageBodyWrapper): List<WikiGeoPageDomain> = data.body.queries.map {
        WikiGeoPageDomain(it.id, it.title)
    }
}