package io.github.thegbguy.barabiseyapp.utils

import com.russhwolf.settings.Settings

private const val KEY_IS_SABISA_NOTIFICATION_ENABLED = "isSabisaNotificationEnabled"

private val settings = Settings()

fun isSabisaNotificationEnabled() = settings.getBoolean(KEY_IS_SABISA_NOTIFICATION_ENABLED, false)

fun setSabisaNotificationEnabled(enabled: Boolean) =
    settings.putBoolean(KEY_IS_SABISA_NOTIFICATION_ENABLED, enabled)