package com.soft.dailynotes.presentation.ui.notes.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import com.soft.dailynotes.presentation.ui.utils.Constants.STOP_TIME_OUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {
    var _settingsUiState: StateFlow<SettingsUiState> = preferenceRepository.isDark.map {
        SettingsUiState(darkMode  = it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLIS),
        SettingsUiState()
    )
    var uiState:StateFlow<SettingsUiState> = preferenceRepository.isEnglish.map {
        SettingsUiState(isEnglish = it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLIS),
        SettingsUiState()
    )

    fun addSetting(mode: Boolean) {
        viewModelScope.launch {
            preferenceRepository.saveModePreference(mode)
        }
    }

    fun addLanguageSetting(mode: Boolean) {
        viewModelScope.launch {
           preferenceRepository.saveLanguagePreference(mode)
        }
    }



}

data class SettingsUiState(
    val darkMode: Boolean = false,
    val isEnglish: Boolean = true
)