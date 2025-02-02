package io.github.thegbguy.barabiseyapp.di

import io.github.thegbguy.barabiseyapp.db.DriverFactory
import org.koin.dsl.module

val iosDbModule = module {
    single {
        DriverFactory()
    }
}