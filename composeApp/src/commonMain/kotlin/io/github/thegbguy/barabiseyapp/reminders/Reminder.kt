package io.github.thegbguy.barabiseyapp.reminders

data class Reminder(
    val id: String,
    val title: String,
    val time: String // e.g., "13th January, 5:00 PM"
)
