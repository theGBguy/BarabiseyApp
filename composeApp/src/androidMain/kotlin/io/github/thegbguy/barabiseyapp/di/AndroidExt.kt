package io.github.thegbguy.barabiseyapp.di

import android.app.Application
import android.content.Context
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(KoinInternalApi::class)
fun KoinApplication.androidContext(androidContext: Context): KoinApplication {
    if (koin.logger.isAt(Level.INFO)) {
        koin.logger.info("[init] declare Android Context")
    }

    koin.loadModules(listOf(
        module {
            if (androidContext is Application) {
                single { androidContext } bind Context::class
            } else {
                single { androidContext }
            }
        }
    ))

    return this
}

inline fun <reified T> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}