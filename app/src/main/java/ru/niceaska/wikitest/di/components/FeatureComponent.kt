package ru.niceaska.wikitest.di.components

import dagger.Subcomponent
import ru.niceaska.wikitest.di.modules.FeatureModule
import ru.niceaska.wikitest.di.scopes.FeatureScope
import ru.niceaska.wikitest.presentation.activities.MainActivity

/**
 * [Subcomponent] логики приложения
 */
@FeatureScope
@Subcomponent(modules = [FeatureModule::class])
interface FeatureComponent {

    fun inject(activity: MainActivity)
}