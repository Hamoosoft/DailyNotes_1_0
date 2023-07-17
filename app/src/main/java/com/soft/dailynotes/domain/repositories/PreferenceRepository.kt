package com.soft.dailynotes.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository{
    suspend fun saveModePreference(isDark: Boolean)
    val isDark: Flow<Boolean>
    suspend fun saveLanguagePreference(isEnglish: Boolean)
    val isEnglish: Flow<Boolean>
    suspend fun saveHomeLayoutPreference(isHomeLayoutGrit: Boolean)
    val isHomeLayoutGrit: Flow<Boolean>
    suspend fun saveArchiveLayoutPreference(isArchiveLayoutGrit: Boolean)
    val isArchiveLayoutGrit: Flow<Boolean>
}
