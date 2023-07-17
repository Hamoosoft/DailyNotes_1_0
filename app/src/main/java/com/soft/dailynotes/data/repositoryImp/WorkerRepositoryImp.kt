package com.soft.dailynotes.data.repositoryImp

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.soft.dailynotes.domain.repositories.WorkerRepository
import com.soft.dailynotes.presentation.ui.utils.Constants.KEY_DURATION
import com.soft.dailynotes.presentation.ui.utils.Constants.KEY_TITLE
import com.soft.dailynotes.worker.ReminderWorker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkerRepositoryImp @Inject constructor(context: Context) : WorkerRepository {
    private val workManager = WorkManager.getInstance(context)
    override val outputWorkInfo: Flow<WorkInfo>
        get() = TODO("Not yet implemented")


    override fun applyReminder(duration: Long, title: String) {
        val reminderRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        reminderRequest.setInputData(createInputDataForWorkRequest(duration, title))
        workManager.enqueue(reminderRequest.build())
    }

    override fun cancelWork() {
        TODO("Not yet implemented")
    }

    private fun createInputDataForWorkRequest(duration: Long, title: String): Data {
        val builder = Data.Builder()
        builder.putString(KEY_TITLE, title).putLong(KEY_DURATION, duration)
        return builder.build()
    }
}