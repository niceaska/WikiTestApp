package ru.niceaska.wikitest.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper
import ru.niceaska.wikitest.data.models.WikiImageBodyWrapper

interface WikiApi {

    @GET("api.php")
    fun getPlacesList(@QueryMap queryMap: Map<String, String>): Single<WikiFePageBodyWrapper>

    @GET("api.php")
    fun getImagesTitles(@QueryMap queryMap: Map<String, String>): Single<WikiImageBodyWrapper>
}