package ru.niceaska.wikitest.di.modules

import dagger.Module
import dagger.Provides
import ru.niceaska.wikitest.data.converters.WikiArticleImagesTitlesConverter
import ru.niceaska.wikitest.data.converters.WikiPlacesConverter
import ru.niceaska.wikitest.di.scopes.FeatureScope
import ru.niceaska.wikitest.domain.interactors.WikiGetImagesTitlesInteractor
import ru.niceaska.wikitest.domain.interactors.WikiGetPagesInteractor
import ru.niceaska.wikitest.domain.repository.DataRepository
import ru.niceaska.wikitest.presentation.converters.UIWidgetPlacesConverter
import ru.niceaska.wikitest.presentation.viewmodels.ViewModelFactory

@Module
class FeatureModule {

    @FeatureScope
    @Provides
    fun provideViewModelViewModelFactory(repository: DataRepository) = ViewModelFactory(
        WikiGetPagesInteractor(repository, WikiPlacesConverter()),
        WikiGetImagesTitlesInteractor(repository, WikiArticleImagesTitlesConverter()),
        UIWidgetPlacesConverter()
    )
}