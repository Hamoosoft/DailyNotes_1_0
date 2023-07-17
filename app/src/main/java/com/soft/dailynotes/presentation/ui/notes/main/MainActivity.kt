package com.soft.dailynotes.presentation.ui.notes.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.soft.dailynotes.presentation.ui.navigation.DailyNotesNavigationGraph
import com.soft.dailynotes.presentation.ui.theme.DailyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val setting: Settings = hiltViewModel()
            val uiState: SettingState? = setting._settingsUiState?.collectAsState()?.value
            val langState: SettingState = setting.uiState.collectAsState().value
            localeLange(this, if (langState.isEnglish) "en" else "de")
            DailyNotesTheme(darkTheme = uiState?.darkMode ?: isSystemInDarkTheme()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DailyNotesNavigationGraph()
                }
            }
        }
    }


}

