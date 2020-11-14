package ru.niceaska.wikitest.rules

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


/**
 * JUnit Test Rule which overrides RxJava and Android schedulers for use in unit tests.
 *
 *
 * All schedulers are replaced with Schedulers.trampoline().
 */
class RxSchedulerRule : TestRule {

    override fun apply(base: Statement, description: Description) =
        object : Statement() {
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { SCHEDULER_INSTANCE }

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler { SCHEDULER_INSTANCE }
                RxJavaPlugins.setNewThreadSchedulerHandler { SCHEDULER_INSTANCE }
                RxJavaPlugins.setComputationSchedulerHandler { SCHEDULER_INSTANCE }

                base.evaluate()
            }
        }

    companion object {
        private val SCHEDULER_INSTANCE: Scheduler = Schedulers.trampoline()
    }
}