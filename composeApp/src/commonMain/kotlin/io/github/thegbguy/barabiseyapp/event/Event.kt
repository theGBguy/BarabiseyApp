package io.github.thegbguy.barabiseyapp.event

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
)