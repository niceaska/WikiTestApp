package ru.niceaska.wikitest.domain.models

/**
 *  Модель домейн слоя списка статей Википедии
 *
 *  @constructor
 *  @property id айди
 *  @property title  заголовок
 */
data class WikiGeoPageDomain(
    val id: Long,
    val title: String,
)
