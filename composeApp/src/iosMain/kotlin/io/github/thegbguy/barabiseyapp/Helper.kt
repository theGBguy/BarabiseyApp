package io.github.thegbguy.barabiseyapp

import io.github.thegbguy.barabiseyapp.di.appModule
import io.github.thegbguy.barabiseyapp.di.commonDbModule
import io.github.thegbguy.barabiseyapp.di.iosDbModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(iosDbModule)
        modules(commonDbModule)
        modules(appModule)
    }
}