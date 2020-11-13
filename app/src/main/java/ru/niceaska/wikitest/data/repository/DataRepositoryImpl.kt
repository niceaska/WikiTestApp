package ru.niceaska.wikitest.data.repository

import android.annotation.SuppressLint
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.niceaska.wikitest.data.GeoDataSource
import ru.niceaska.wikitest.data.api.WikiApi
import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper
import ru.niceaska.wikitest.data.models.WikiImageData
import ru.niceaska.wikitest.domain.repository.DataRepository


class DataRepositoryImpl(private val geoDataSource: GeoDataSource) : DataRepository {

    private val wikiApi: WikiApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        retrofit.create(WikiApi::class.java)
    }

    @SuppressLint("CheckResult")
    override fun getWikiPlacesList(force: Boolean): Single<WikiFePageBodyWrapper?> {
        if (force) {
            geoDataSource.updateLocation()
        } else {
            geoDataSource.requestLocation()
        }
        return geoDataSource.locationData.map { point ->
            val latitude = point.latitude
            val longitude = point.longitude
            mapOf(
                "action" to "query",
                "list" to "geosearch",
                "gsradius" to "10000",
                "gscoord" to "$latitude|$longitude",
                "gslimit" to "50",
                "format" to "json"
            )
        }.flatMap {
            wikiApi.getPlacesList(it).toObservable()
        }.firstOrError()

    }

    override fun getImagesNames(id: Long): Single<List<WikiImageData>> {
        val queryMap = mapOf(
            "action" to "query",
            "prop" to "images",
            "pageids" to "$id",
            "format" to "json"
        )
        return wikiApi.getImagesTitles(queryMap).map {
            val jsonNode = it.others["$id"]
            ObjectMapper().readerFor(
                object : TypeReference<List<WikiImageData>?>() {}
            ).readValue(jsonNode)
        }
    }

    companion object {
        private const val BASE_URL = "https://en.wikipedia.org/w/"
    }
}