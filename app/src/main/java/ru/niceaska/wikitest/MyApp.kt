package ru.niceaska.wikitest

import android.app.Application
import ru.niceaska.wikitest.di.components.AppComponent
import ru.niceaska.wikitest.di.components.DaggerAppComponent
import ru.niceaska.wikitest.di.components.FeatureComponent

class MyApp : Application() {

    /**
     * Фичевый компонент приложения
     */
    private var featureComponent: FeatureComponent? = null
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build();
    }

    /**
     * Инициализировать [FeatureComponent] приложения
     */
    fun initFeatureComponent(): FeatureComponent? = appComponent.featureComponent()

    /**
     * Уничтожить [FeatureComponent] приложения
     */
    fun destroyFeatureComponent() {
        featureComponent = null;
    }
}