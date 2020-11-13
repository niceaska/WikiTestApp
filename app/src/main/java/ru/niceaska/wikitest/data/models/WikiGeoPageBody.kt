package ru.niceaska.wikitest.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiGeoPageBody @JsonCreator constructor(
    @param:JsonProperty("geosearch")
    var queries: List<WikiGeoPageData> = emptyList()
)