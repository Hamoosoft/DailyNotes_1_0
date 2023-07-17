package com.soft.dailynotes.presentation.ui.notes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import com.soft.dailynotes.presentation.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class Settings @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {
    var _settingsUiState: StateFlow<SettingState> = preferenceRepository.isDark.map {
        SettingState(darkMode = it)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(Constants.STOP_TIME_OUT_MILLIS),
        SettingState()
    )
    var uiState: StateFlow<SettingState> = preferenceRepository.isEnglish.map {
        SettingState(isEnglish = it)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(Constants.STOP_TIME_OUT_MILLIS),
        SettingState()
    )


}

data class SettingState(val darkMode: Boolean = false, val isEnglish: Boolean = true)