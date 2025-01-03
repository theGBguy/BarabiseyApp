package io.github.thegbguy.barabiseyapp.utils

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.datetime.LocalTime
import java.util.concurrent.TimeUnit

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    companion object {
        const val TAG: String = "DailyQuoteWorker"

        fun enqueueOneTimeWork(context: Context, notificationTime: LocalTime) {
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(
                    calculateInitialDelay(notificationTime),
                    TimeUnit.MILLISECONDS
                )
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                TAG,
                ExistingWorkPolicy.REPLACE,  // Update if there's already a worker
                oneTimeWorkRequest
            )
        }
    }

    override fun doWork(): Result {
        showPushNotification()
        return Result.success()
    }
}
