package ru.niceaska.wikitest.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Элемень списка статей по координатам
 *
 * @constructor
 * @property id айди
 * @property title  заголовок
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiGeoPageData @JsonCreator constructor(
    @param:JsonProperty("pageid")
    var id: Long,
    @param:JsonProperty("title")
    var title: String,
)
