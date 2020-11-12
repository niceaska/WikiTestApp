package ru.niceaska.wikitest.data.repository

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.niceaska.wikitest.data.api.WikiApi
import ru.niceaska.wikitest.domain.repository.DataRepository

class DataRepositoryImpl : DataRepository {

    private val wikiApi: WikiApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        retrofit.create(WikiApi::class.java)
    }

    override fun getWikiPlacesList(latitude: Long, longitude: Long) {
        val queryMap = mapOf(
            "action" to "query",
            "list" to "geosearch",
            "gsradius" to "10000",
            "gscoord" to "$latitude|$longitude",
            "gslimit" to "50",
            "format" to "json"
        )
        wikiApi.getImagesTitles(queryMap)
    }

    override fun getImagesNames(article: CharSequence) {
    }

    companion object {
        private const val BASE_URL = "https://en.wikipedia.org/w"
    }
}