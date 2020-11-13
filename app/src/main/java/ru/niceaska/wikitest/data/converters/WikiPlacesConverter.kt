package ru.niceaska.wikitest.data.converters

import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper
import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain

/**
 * Конвертер списка статей по координатам в из дата в домейн слой
 */
class WikiPlacesConverter {

    /**
     * Конвертерует [WikiFePageBodyWrapper] в [WikiGeoPageDomain] - сущность домейн слоя
     */
    fun convert(data: WikiFePageBodyWrapper): List<WikiGeoPageDomain> = data.body.queries.map {
        WikiGeoPageDomain(it.id, it.title)
    }
}