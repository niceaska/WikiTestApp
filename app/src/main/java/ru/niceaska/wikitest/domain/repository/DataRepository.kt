package ru.niceaska.wikitest.domain.repository

import io.reactivex.Single
import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper

interface DataRepository {

    fun getWikiPlacesList(force: Boolean): Single<WikiFePageBodyWrapper?>

    fun getImagesNames(id: Long)
}