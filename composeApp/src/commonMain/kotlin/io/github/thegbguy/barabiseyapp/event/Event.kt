package io.github.thegbguy.barabiseyapp.event

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
    val image: String,
) {
    fun asShareableString() = buildString {
        appendLine("Title: $title")
        appendLine("Description: $description")
        appendLine("Date: $date")
        appendLine("Time: $time")
        appendLine("Location: $location")
        appendLine("Image: $image")
    }
}