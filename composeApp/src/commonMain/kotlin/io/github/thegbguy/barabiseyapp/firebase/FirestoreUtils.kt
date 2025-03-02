package io.github.thegbguy.barabiseyapp.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.github.thegbguy.barabiseyapp.event.Event
import io.github.thegbguy.barabiseyapp.reminders.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

const val EVENTS_COLLECTION = "events"
const val REMINDERS_COLLECTION = "reminders"

fun getEvents() = Firebase.firestore
    .collection(EVENTS_COLLECTION)
    .snapshots
    .map { listSnapshot ->
        listSnapshot.documents.map { documentSnapshot -> documentSnapshot.data() as Event }
    }.flowOn(Dispatchers.IO)

fun getReminders() = Firebase.firestore
    .collection(REMINDERS_COLLECTION)
    .snapshots
    .map { listSnapshot ->
        listSnapshot.documents.map { documentSnapshot -> documentSnapshot.data() as Reminder }
    }.flowOn(Dispatchers.IO)