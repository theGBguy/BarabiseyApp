package io.github.thegbguy.barabiseyapp.utils

import android.content.Context
import io.github.thegbguy.barabiseyapp.di.getKoinInstance
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

actual fun scheduleNotification() {
    val appContext: Context = getKoinInstance()
    val timeNextMinute = Clock.System.now().plus(1, DateTimeUnit.MINUTE)
        .toLocalDateTime(TimeZone.currentSystemDefault()).time
    ReminderWorker.enqueueOneTimeWork(
        appContext,
        LocalTime(timeNextMinute.hour, timeNextMinute.minute)
    )
}

