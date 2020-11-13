package ru.niceaska.wikitest.di.modules

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import ru.niceaska.wikitest.data.datasource.GeoDataSource
import ru.niceaska.wikitest.data.repository.DataRepositoryImpl
import ru.niceaska.wikitest.domain.repository.DataRepository
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideDataRepository(application: Application): DataRepository = DataRepositoryImpl(
        GeoDataSource(LocationServices.getFusedLocationProviderClient(application)),
        ObjectMapper()
    )
}