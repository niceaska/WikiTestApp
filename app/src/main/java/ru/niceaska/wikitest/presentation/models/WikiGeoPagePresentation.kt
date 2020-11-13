package ru.niceaska.wikitest.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WikiGeoPagePresentation(
    val id: Long,
    val title: String,
) : Parcelable