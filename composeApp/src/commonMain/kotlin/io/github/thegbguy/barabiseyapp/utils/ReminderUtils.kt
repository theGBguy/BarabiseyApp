package io.github.thegbguy.barabiseyapp.utils

import com.mmk.kmpnotifier.notification.NotificationImage
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

fun showPushNotification(
    title: String? = "Sample Notification Title",
    body: String? = "Sample notification body"
) {
    NotifierManager.getLocalNotifier().notify {
        this.id = Random.nextInt(0, Int.MAX_VALUE)
        this.title = title ?: "Sample Notification Title"
        this.body = body ?: "Sample notification body"
    }
}

fun showPushNotificationWithPayload(
    payloadData: PayloadData = emptyMap<String, Any>()
) {
    NotifierManager.getLocalNotifier().notify {
        this.id = Random.nextInt(0, Int.MAX_VALUE)
        this.title = payloadData["title"]?.toString() ?: "Sample Notification Title"
        this.body = payloadData["body"]?.toString() ?: "Sample notification body"
        this.payloadData = payloadData as? Map<String, String> ?: emptyMap()
        this.image = payloadData["imageUrl"]?.toString()?.let { NotificationImage.Url(it) }
    }
}

fun calculateInitialDelay(notificationTime: LocalTime): Long {
    // Get the current instant and convert it to the current system's LocalDateTime
    val now = Clock.System.now()
    val nowDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

    // Combine the current date with the target notification time
    var targetDateTime = LocalDateTime(nowDateTime.date, notificationTime)

    // If the target time is in the past for today, move to the next day
    if (targetDateTime < nowDateTime) {
        targetDateTime = LocalDateTime(nowDateTime.date.plus(1, DateTimeUnit.DAY), notificationTime)
    }

    // Convert targetDateTime back to Instant
    val targetInstant = targetDateTime.toInstant(TimeZone.currentSystemDefault())

    // Calculate the delay in milliseconds
    val delayInMillis = targetInstant.toEpochMilliseconds() - now.toEpochMilliseconds()

    return delayInMillis
}


expect fun scheduleNotification()