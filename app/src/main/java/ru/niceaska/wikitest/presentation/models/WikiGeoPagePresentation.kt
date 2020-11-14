package ru.niceaska.wikitest.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  Модель презентационного слоя списка статей Википедии
 *
 *  @constructor
 *  @property id айди
 *  @property title  заголовок
 */
@Parcelize
data class WikiGeoPagePresentation(
    val id: Long,
    val title: String,
) : Parcelable