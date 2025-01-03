package io.github.thegbguy.barabiseyapp

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import io.github.thegbguy.barabiseyapp.utils.AppInitializer

class BarabiseyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.onApplicationStart()

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = io.github.thegbguy.R.mipmap.ic_launcher,
                showPushNotification = true,
            )
        )
    }
}