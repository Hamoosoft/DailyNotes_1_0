package com.soft.dailynotes.presentation.ui.notes.archive

import ArchiveNotesItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.soft.dailynotes.R
import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.presentation.ui.notes.details.ReminderDialog
import com.soft.dailynotes.presentation.ui.notes.home.BottonNavigation
import com.soft.dailynotes.presentation.ui.notes.home.HomeNotesTopAppBar
import com.soft.dailynotes.presentation.ui.notes.home.SearchBar
import com.soft.dailynotes.presentation.ui.utils.Constants.ARCHIVE


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ArchiveScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateUp: () -> Unit = {}
) {
    val archiveViewModel: ArchiveViewModel = hiltViewModel()
    val archiveState = archiveViewModel.archive.collectAsState().value
    val searchNotesUiState: List<Archive> = archiveViewModel.searchUiState.collectAsState().value
    val text = archiveViewModel.searchText.collectAsState().value
    var applyNotificationDialog by rememberSaveable { mutableStateOf(false) }
    var isGridLayout by rememberSaveable { mutableStateOf(true) }
    var currentNoteTitle by rememberSaveable { mutableStateOf("") }
    var currentNoteNoteId by rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    Scaffold(topBar = {
        HomeNotesTopAppBar(title = ARCHIVE, isNavigateBack = true, navigateUp = { onNavigateUp() })
    }, bottomBar = {
        BottomAppBar(
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            BottonNavigation(navController = navController)
        }
    }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (archiveState.notes.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.empty_archive_message),
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            } else {

                SearchBar(
                    text = text,
                    onSearchChanged = {archiveViewModel.onSearchChanged(it) },
                    isGridLayoutChanged = { isGridLayout = !isGridLayout

                                          archiveViewModel.safeLayoutState(isGridLayout = !isGridLayout)
                                          },
                    isGridLayout = archiveViewModel.getLayoutStatus()

                )

                if (archiveViewModel.getLayoutStatus()) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = dimensionResource(id = R.dimen.min_GridSize)),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.small_padding))
                    ) {
                        itemsIndexed(searchNotesUiState) { index, item ->
                            ArchiveNotesItem(
                                notes = item,
                                onLongClick = {

                                },
                                onClick = {


                                },
                                applyNotificationDialog = {title,id ->
                                    applyNotificationDialog = true
                                    currentNoteTitle = title
                                    currentNoteNoteId =id

                                },
                                deleteNoteFromArchiveEvent = { archiveViewModel.deleteNote(it) },
                                restoreNoteToNotesRepository = {
                                    archiveViewModel.restoreNoteToNotesRepository(it)
                                }, context = context
                            )

                        }

                    }
                } else {

                    LazyColumn(contentPadding = PaddingValues(dimensionResource(id = R.dimen.small_padding))) {
                        items(searchNotesUiState) { item ->

                            ArchiveNotesItem(
                                notes = item,
                                onLongClick = {

                                },
                                onClick = {


                                },
                                applyNotificationDialog = { title,id ->
                                    applyNotificationDialog = true
                                    currentNoteTitle = title
                                    currentNoteNoteId =id

                                },
                                deleteNoteFromArchiveEvent = { archiveViewModel.deleteNote(it) },
                                restoreNoteToNotesRepository = {
                                    archiveViewModel.restoreNoteToNotesRepository(it)
                                }, context = context
                            )
                        }
                    }


                }

            }

        }

    }
    if (applyNotificationDialog) {
        ReminderDialog(
            onDismiss = { applyNotificationDialog = false },
            reminderData = {
                archiveViewModel.scheduleReminder(
                    title = currentNoteTitle, duration = it, noteId = currentNoteNoteId
                )
            }, context
        )
    }


}



