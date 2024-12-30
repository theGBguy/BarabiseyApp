package io.github.thegbguy.barabiseyapp

import android.app.Application
import io.github.thegbguy.barabiseyapp.utils.AppInitializer

class BarabiseyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.onApplicationStart()
    }
}