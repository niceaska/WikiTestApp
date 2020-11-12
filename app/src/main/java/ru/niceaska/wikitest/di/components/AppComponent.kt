package ru.niceaska.wikitest.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.niceaska.wikitest.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}