package ru.niceaska.wikitest.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiImageBody @JsonCreator constructor(
    @param:JsonProperty("images")
    var imageTitles: List<WikiImageData> = emptyList()
)