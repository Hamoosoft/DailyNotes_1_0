package com.soft.dailynotes.data.repositoryImp

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePreferenceRepositoryImp:PreferenceRepository {
    companion object {
        val IS_ENGLISH = booleanPreferencesKey("is_English")
        val IS_DARK = booleanPreferencesKey("is_DARK")
        val IS_HOME_LAYOUT = booleanPreferencesKey("isHomeLayoutGrit")
        val IS_ARCHIVE_LAYOUT = booleanPreferencesKey("isArchiveLayoutGrit")
        const val TAG = "LangPreferenceRepository"
    }
      var  savedValueIsDark:Boolean = false
     var  savedValueIsEnglish:Boolean = false
     var  savedValueIsHome:Boolean = false
     var  savedValueIsArchive:Boolean = false
    override suspend fun saveModePreference(isDark: Boolean) {
        savedValueIsDark =isDark
    }

    override val isDark: Flow<Boolean> = MutableStateFlow(savedValueIsDark)


    override suspend fun saveLanguagePreference(isEnglish: Boolean) {
        savedValueIsEnglish = isEnglish
    }

    override val isEnglish: Flow<Boolean> = MutableStateFlow(savedValueIsEnglish)

    override suspend fun saveHomeLayoutPreference(isHomeLayoutGrit: Boolean) {
        savedValueIsHome = isHomeLayoutGrit
    }

    override val isHomeLayoutGrit: Flow<Boolean> = MutableStateFlow(savedValueIsHome)

    override suspend fun saveArchiveLayoutPreference(isArchiveLayoutGrit: Boolean) {
        savedValueIsArchive =isArchiveLayoutGrit
    }

    override val isArchiveLayoutGrit: Flow<Boolean> = MutableStateFlow(savedValueIsArchive)
}