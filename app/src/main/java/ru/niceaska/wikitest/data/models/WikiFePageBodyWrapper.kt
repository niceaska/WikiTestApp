package ru.niceaska.wikitest.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Модель запроса для списка статей по координатам
 *
 * @constructor
 * @property body тело запроса
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiFePageBodyWrapper @JsonCreator constructor(
    @param:JsonProperty("query")
    var body: WikiGeoPageBody
)