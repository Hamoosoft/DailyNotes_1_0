package com.soft.dailynotes.domain.repositories

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface WorkerRepository {
    val outputWorkInfo: Flow<WorkInfo>
    fun applyReminder(duration: Long,title:String)
    fun cancelWork()
}