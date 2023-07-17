package com.soft.dailynotes.data.repositoryImp

import androidx.work.WorkInfo
import com.soft.dailynotes.domain.repositories.WorkerRepository
import kotlinx.coroutines.flow.Flow

class FakeWorkRepositoryImp:WorkerRepository {
    override val outputWorkInfo: Flow<WorkInfo>
        get() = TODO("Not yet implemented")

    override fun applyReminder(duration: Long, title: String) {
        TODO("Not yet implemented")
    }

    override fun cancelWork() {
        TODO("Not yet implemented")
    }
}