package ru.niceaska.wikitest.presentation.converters

import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

/**
 * Конвертер сущности домейн слоя списка статей в презентационный слой
 */
class UIWidgetPlacesConverter {

    /**
     *  Конвертирует сущности домейн слоя [WikiGeoPageDomain] списка
     *  статей в презентационный слой [WikiGeoPagePresentation]
     */
    fun convert(data: List<WikiGeoPageDomain>): List<WikiGeoPagePresentation> = data.map {
        WikiGeoPagePresentation(it.id, it.title)
    }
}