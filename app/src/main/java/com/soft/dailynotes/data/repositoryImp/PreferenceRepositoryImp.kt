package com.soft.dailynotes.data.repositoryImp

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LangPreferenceRepository @Inject constructor(private val dataStore : DataStore<Preferences>):
    PreferenceRepository {
    companion object {
        val IS_ENGLISH = booleanPreferencesKey("is_English")
        val IS_DARK = booleanPreferencesKey("is_DARK")
        val IS_HOME_LAYOUT = booleanPreferencesKey("isHomeLayoutGrit")
        val IS_ARCHIVE_LAYOUT = booleanPreferencesKey("isArchiveLayoutGrit")
        const val TAG = "LangPreferenceRepository"
    }
    override suspend fun saveLanguagePreference(isEnglish: Boolean) {
        dataStore.edit {
                preferences ->
            preferences[IS_ENGLISH] = isEnglish
        }
    }
    override suspend fun saveModePreference(isDark: Boolean) {
        dataStore.edit {
                preferences ->
            preferences[IS_DARK] = isDark
        }
    }
    override val isEnglish: Flow<Boolean> = dataStore.data.catch { preferences->
        if(preferences is IOException) {
            Log.e(TAG, "Error reading preferences.", preferences)
            emit(emptyPreferences())
        } else {
            throw preferences
        }
    }.map { preferences ->
        preferences[IS_ENGLISH] ?: true
    }

    override suspend fun saveHomeLayoutPreference(isHomeLayoutGrit: Boolean) {
        dataStore.edit {
                preferences ->
            preferences[IS_HOME_LAYOUT] = isHomeLayoutGrit
        }
    }

    override val isHomeLayoutGrit: Flow<Boolean> = dataStore.data.catch {
        if(it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[IS_HOME_LAYOUT] ?: true
    }

    override suspend fun saveArchiveLayoutPreference(isArchiveLayoutGrit: Boolean) {
        dataStore.edit {
                preferences ->
            preferences[IS_ARCHIVE_LAYOUT] = isArchiveLayoutGrit
        }
    }

    override val isArchiveLayoutGrit: Flow<Boolean> = dataStore.data.catch {
        if(it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[IS_ARCHIVE_LAYOUT] ?: true
    }
    override val isDark: Flow<Boolean> = dataStore.data.catch {
        if(it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[IS_DARK] ?: false
    }
}