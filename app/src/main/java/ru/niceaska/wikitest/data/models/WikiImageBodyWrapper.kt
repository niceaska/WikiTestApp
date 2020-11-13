package ru.niceaska.wikitest.data.models

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.JsonNode

/**
 * Модель для заголвоков картинок
 *
 * @constructor
 * @property pages мапа значений из жсон
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiImageBodyWrapper @JsonCreator constructor(
    @JsonAnySetter
    @get:JsonAnyGetter
    var pages: Map<String, JsonNode> = LinkedHashMap()
)