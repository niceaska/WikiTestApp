package ru.niceaska.wikitest.data.converters

import ru.niceaska.wikitest.data.models.WikiImageData

/**
 * Конвертер списка заголовков картинок в домейн слой
 */
class WikiArticleImagesTitlesConverter {

    /**
     * Конвертирует [WikiImageData] в сущность domain слоя
     */
    fun convert(data: List<WikiImageData>): List<String> = data.map { it.title }
}