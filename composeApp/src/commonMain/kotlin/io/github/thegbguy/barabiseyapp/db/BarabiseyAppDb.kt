package io.github.thegbguy.barabiseyapp.db

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import io.github.thegbguy.barabiseyapp.AppDatabase
import io.github.thegbguy.barabiseyapp.Reminders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

class BarabiseyAppDb(driverFactory: DriverFactory) {
    private val database = AppDatabase(
        driver = driverFactory.createDriver(),
        remindersAdapter = Reminders.Adapter(
            repeatIntervalAdapter = EnumColumnAdapter()
        )
    )
    private val dbQuery = database.reminderQueries

    fun getAllReminders(): Flow<List<Reminders>> {
        return dbQuery.getAllReminders().asFlow().mapToList(Dispatchers.IO)
    }

    suspend fun insertReminder(reminder: Reminders) = withContext(Dispatchers.IO) {
        dbQuery.insertReminder(
            title = reminder.title,
            details = reminder.details,
            dateTime = reminder.dateTime,
            repeatInterval = reminder.repeatInterval
        )
    }

    suspend fun deleteReminder(id: Long) = withContext(Dispatchers.IO) {
        dbQuery.deleteReminder(id)
    }
}