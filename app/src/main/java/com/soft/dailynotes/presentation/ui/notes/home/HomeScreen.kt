package com.soft.dailynotes.presentation.ui.notes.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavHostController
import com.soft.dailynotes.R
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.presentation.ui.notes.details.ReminderDialog
import com.soft.dailynotes.presentation.ui.utils.Constants.ADD_NOTE_KEY


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteHomeScreen(
    modifier: Modifier = Modifier,
    homeNoteViewModel: HomeViewModel,
    onNoteClick: (id: Int) -> Unit,
    navController: NavHostController,
    onNavigateToDetails: (Int) -> Unit,
    onNavigateUp: () -> Unit
) {
    val homeNotesUiState: HomeNotesUiState = homeNoteViewModel.homeUiState.collectAsState().value
    val searchNotesUiState: List<Notes> = homeNoteViewModel.searchUiState.collectAsState().value
    val text = homeNoteViewModel.searchText.collectAsState().value
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var animatedVisibilityStatus by rememberSaveable { mutableStateOf(false) }
    var applyNotificationDialog by rememberSaveable { mutableStateOf(false) }
    var currentNoteTitle by rememberSaveable { mutableStateOf("") }
    var selectedNote: Notes? = null
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigateToDetails(ADD_NOTE_KEY)
            }, backgroundColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = {
            HomeNotesTopAppBar(
                title = stringResource(id = R.string.app_name),
                onSignOut = { onNavigateUp() },
                animatedVisibilityStatus = {
                    animatedVisibilityStatus = !animatedVisibilityStatus
                },
                isHomeNotEmpty = homeNotesUiState.notes.isNotEmpty()
            )
        }, bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(50)
                ), backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                BottonNavigation(navController = navController)
            }

        }, isFloatingActionButtonDocked = true
    ) {

        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            if (homeNotesUiState.notes.isEmpty()) {
                HomeScreenEmpty()
            } else {
                AnimatedVisibility(
                    visible = animatedVisibilityStatus,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderByItems(
                        onOrderByClick = {
                            homeNoteViewModel.orderBy(
                                notesOrderBy = it,
                                noteOrderType = homeNotesUiState.notesOrderType
                            )

                        }, onOrderTypeClick = {
                            homeNoteViewModel.orderBy(
                                noteOrderType = it,
                                notesOrderBy = homeNotesUiState.notesOrderBy
                            )
                        },
                        homeNotesUiState = homeNotesUiState
                    )
                }

                SearchBar(
                    text = text,
                    onSearchChanged = { homeNoteViewModel.onSearchChanged(it) },
                    isGridLayoutChanged = {
                        homeNoteViewModel.saveLayoutStatus()

                    },
                    isGridLayout = homeNoteViewModel.getLayoutStatus()

                )


                if (homeNoteViewModel.getLayoutStatus()) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = dimensionResource(id = R.dimen.min_GridSize)),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.small_padding))
                    ) {

                        itemsIndexed(searchNotesUiState) { index, item ->
                            NotesItem(
                                notes = item,
                                onLongClick = {
                                    openDialog = !openDialog
                                    selectedNote = item

                                },
                                onClick = { onNoteClick(it) },
                                homeNoteViewModel = homeNoteViewModel,
                                applyNotificationDialog = {
                                    applyNotificationDialog = true
                                    currentNoteTitle = it
                                },
                                onArchiveIconClick = { homeNoteViewModel.onArchiveRequest(it) },
                                context = context
                            )

                        }

                    }
                } else {
                    LazyColumn(
                        modifier = modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.small_padding))
                    ) {
                        itemsIndexed(searchNotesUiState) { index, item ->
                            NotesItem(
                                notes = item,
                                onLongClick = {
                                    openDialog = !openDialog
                                    selectedNote = item

                                },
                                onClick = { onNoteClick(it) },
                                applyNotificationDialog = {
                                    applyNotificationDialog = true
                                    currentNoteTitle = it
                                },
                                onArchiveIconClick = {
                                    homeNoteViewModel.onArchiveRequest(it)
                                }, homeNoteViewModel = homeNoteViewModel, context = context
                            )

                        }
                    }

                }

            }
        }
        if (openDialog) {
            DeleteNoteDialog(
                note = selectedNote!!,
                onConfirmClick = {
                    homeNoteViewModel.deleteNote(selectedNote!!)
                    openDialog = !openDialog

                }, onArchiveClick = {
                    homeNoteViewModel.onArchiveRequest(selectedNote!!)
                    openDialog = !openDialog
                }
            ) {
                openDialog = !openDialog
            }
        }
    }
    if (applyNotificationDialog) {
        ReminderDialog(
            onDismiss = { applyNotificationDialog = false },
            reminderData = {
                homeNoteViewModel.scheduleReminder(
                    title = currentNoteTitle, duration = it
                )
            }, context
        )
    }

}

@Composable
fun HomeScreenEmpty(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.no_notes_message),
            style = MaterialTheme.typography.displayLarge
        )
    }
}



