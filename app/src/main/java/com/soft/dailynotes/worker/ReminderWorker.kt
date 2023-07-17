package com.soft.dailynotes.worker

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.soft.dailynotes.R
import com.soft.dailynotes.presentation.ui.utils.Constants.KEY_DURATION
import com.soft.dailynotes.presentation.ui.utils.Constants.KEY_TITLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ReminderWorker(appContext: Context, params: WorkerParameters) :

    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        val title = inputData.getString(KEY_TITLE)
        val duration = inputData.getLong(KEY_DURATION, 1000)

        return withContext(Dispatchers.IO){

            return@withContext try {
                delay(duration)

        makeStatusNotification(title?:"title",applicationContext)
                val outputData = workDataOf("" to "")

                Result.success(outputData)
            } catch (throwable: Throwable) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_applying_reminder),
                    throwable
                )
                Result.failure()
            }
        }
    }
}