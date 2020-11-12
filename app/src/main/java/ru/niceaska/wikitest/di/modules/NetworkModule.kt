package ru.niceaska.wikitest.di.modules

import dagger.Module
import dagger.Provides
import ru.niceaska.wikitest.data.repository.DataRepositoryImpl
import ru.niceaska.wikitest.domain.repository.DataRepository
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Singleton
    @Provides
    fun provideDataRepository(): DataRepository = DataRepositoryImpl()
}