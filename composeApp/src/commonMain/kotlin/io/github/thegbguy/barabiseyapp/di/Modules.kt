package io.github.thegbguy.barabiseyapp.di

import io.github.thegbguy.barabiseyapp.db.BarabiseyAppDb
import io.github.thegbguy.barabiseyapp.reminders.RemindersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val commonDbModule = module {
    single { BarabiseyAppDb(get()) }
}

val appModule = module {
    viewModel { RemindersViewModel(get()) }
}