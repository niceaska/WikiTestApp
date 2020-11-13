package ru.niceaska.wikitest

import android.app.Application
import ru.niceaska.wikitest.di.components.AppComponent
import ru.niceaska.wikitest.di.components.DaggerAppComponent
import ru.niceaska.wikitest.di.components.FeatureComponent

class MyApp : Application() {

    var featureComponent: FeatureComponent? = null
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build();
    }

    fun initListComponent(): FeatureComponent? = appComponent.featureComponent()

    fun destroyListComponent() {
        featureComponent = null;
    }
}