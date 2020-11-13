package ru.niceaska.wikitest.data.models

/**
 * Модель точки координат
 *
 * @constructor
 * @property latitude широта
 * @property longitude долгота
 */
data class GeoPoint(
    val latitude: Double,
    val longitude: Double
)
