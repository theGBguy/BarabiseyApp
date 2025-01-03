package io.github.thegbguy.barabiseyapp.reminders

import kotlinx.serialization.Serializable

@Serializable
data class Reminder(
    val id: String,
    val title: String,
    val details: String? = null,
    val dateTime: String,
    val repeatInterval: RepeatInterval? = null
)

// Enum for defining repeat intervals
@Serializable
enum class RepeatInterval {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}
