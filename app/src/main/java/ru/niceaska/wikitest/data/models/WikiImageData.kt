package ru.niceaska.wikitest.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Элемент списка статей
 *
 * @constructor
 * @property title заголовой
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiImageData @JsonCreator constructor(
    @param:JsonProperty("title")
    var title: String,
)