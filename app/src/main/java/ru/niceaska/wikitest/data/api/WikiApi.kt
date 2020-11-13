package ru.niceaska.wikitest.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper
import ru.niceaska.wikitest.data.models.WikiImageBodyWrapper

/**
 * Интуфейс апи для сетевых запросов
 */
interface WikiApi {

    /**
     * Получить спиок статей по координатам
     *
     * @param queryMap мапа параметром для запроса
     */
    @GET("api.php")
    fun getPlacesList(@QueryMap queryMap: Map<String, String>): Single<WikiFePageBodyWrapper>

    /**
     * Получить спиок заголовокв картинок из статей
     *
     * @param queryMap мапа параметром для запроса
     */
    @GET("api.php")
    fun getImagesTitles(@QueryMap queryMap: Map<String, String>): Single<WikiImageBodyWrapper>
}