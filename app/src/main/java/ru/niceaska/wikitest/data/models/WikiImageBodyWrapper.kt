package ru.niceaska.wikitest.data.models

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.JsonNode

@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiImageBodyWrapper @JsonCreator constructor(
    @param:JsonProperty("pages")
    @JsonAnySetter
    @get:JsonAnyGetter
    val others: Map<String, JsonNode> = LinkedHashMap()
)