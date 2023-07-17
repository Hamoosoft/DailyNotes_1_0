package com.soft.dailynotes.presentation.ui.notes.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.soft.dailynotes.R.*
import com.soft.dailynotes.presentation.ui.notes.home.BottonNavigation
import com.soft.dailynotes.presentation.ui.notes.home.HomeNotesTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUs(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateUp: () -> Unit = {}
) {
    Scaffold(topBar = {
        HomeNotesTopAppBar(
            title = stringResource(id = string.about),
            isNavigateBack = true,
            navigateUp = { onNavigateUp() })
    }, bottomBar = {
        BottomAppBar(
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            BottonNavigation(navController = navController)
        }
    }) {
        PersonalInfoSection(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .verticalScroll(
                    rememberScrollState()
                )
        )


    }
}




