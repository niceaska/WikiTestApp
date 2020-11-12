package ru.niceaska.wikitest.domain.repository

interface DataRepository {

    fun getWikiPlacesList(latitude: Long, longitude: Long)

    fun getImagesNames(article: CharSequence)
}