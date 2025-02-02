package io.github.thegbguy.barabiseyapp.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.thegbguy.barabiseyapp.Reminders
import io.github.thegbguy.barabiseyapp.db.BarabiseyAppDb
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RemindersViewModel(
    private val db: BarabiseyAppDb
) : ViewModel() {

    private val _reminders = db.getAllReminders()
        .map { remindersList ->
            remindersList.map { reminder ->
                Reminder(
                    id = reminder.id.toString(),
                    title = reminder.title,
                    details = reminder.details,
                    dateTime = reminder.dateTime,
                    repeatInterval = reminder.repeatInterval
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val reminders = _reminders

    fun insertReminder() = viewModelScope.launch {
        val reminder = Reminders(
            id = -1,
            title = "Test reminder",
            details = "Test reminder details",
            dateTime = "Test date time",
            repeatInterval = RepeatInterval.DAILY
        )
        db.insertReminder(reminder)
    }

    fun deleteReminder(id: Long) = viewModelScope.launch {
        db.deleteReminder(id)
    }
}