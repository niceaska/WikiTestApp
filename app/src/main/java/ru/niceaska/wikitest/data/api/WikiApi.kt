package ru.niceaska.wikitest.data.api

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WikiApi {

    @GET("/api.php")
    fun getPlacesList(@QueryMap queryMap: Map<String, String>)

    @GET("/api.php")
    fun getImagesTitles(@QueryMap queryMap: Map<String, String>)
}