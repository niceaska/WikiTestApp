package ru.niceaska.wikitest.data

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import io.reactivex.subjects.PublishSubject
import ru.niceaska.wikitest.data.exceptions.RequestLocationException
import ru.niceaska.wikitest.data.exceptions.UpdateLocationException
import ru.niceaska.wikitest.data.models.GeoPoint


@SuppressLint("MissingPermission")
class GeoDataSource(
    private val locationProvider: FusedLocationProviderClient
) {

    val locationData: PublishSubject<GeoPoint> = PublishSubject.create()

    fun requestLocation() =
        locationProvider.lastLocation.addOnSuccessListener(
            creteSuccessListener(
                RequestLocationException()
            )
        )

    fun updateLocation() = locationProvider
        .getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener(creteSuccessListener(UpdateLocationException()))

    private fun creteSuccessListener(error: Throwable): (Location?) -> Unit = { location ->
        location?.let {
            locationData.onNext(GeoPoint(latitude = it.latitude, longitude = it.longitude))
        } ?: locationData.onError(error)
    }
}