package ru.niceaska.wikitest.presentation.converters

import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

class UIWidgetPlacesConverter {
    fun convert(data: List<WikiGeoPageDomain>): List<WikiGeoPagePresentation> = data.map {
        WikiGeoPagePresentation(it.id, it.title)
    }
}