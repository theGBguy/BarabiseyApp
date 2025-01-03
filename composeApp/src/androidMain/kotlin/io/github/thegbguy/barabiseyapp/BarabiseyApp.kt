package io.github.thegbguy.barabiseyapp

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import io.github.thegbguy.barabiseyapp.di.androidContext
import io.github.thegbguy.barabiseyapp.utils.AppInitializer
import org.koin.core.context.startKoin

class BarabiseyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BarabiseyApp)
        }

        AppInitializer.onApplicationStart()

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.mipmap.ic_launcher,
                showPushNotification = true,
            )
        )
    }
}