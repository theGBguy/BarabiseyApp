package io.github.thegbguy.barabiseyapp.utils

import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData

object AppInitializer {
    fun onApplicationStart() {
        onApplicationStartPlatformSpecific()

        GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = "522253599310-b5e020hmnrgtktkifikhaov0vi89i0np.apps.googleusercontent.com"))

        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onPushNotification(title: String?, body: String?) {
                showPushNotification(
                    title = title,
                    body = body
                )
            }

            override fun onPayloadData(data: PayloadData) {
                showPushNotificationWithPayload(data)
            }
        })
    }
}