package com.soft.dailynotes.presentation.ui.notes.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.soft.dailynotes.R
import com.soft.dailynotes.presentation.ui.navigation.NavDestination
import com.soft.dailynotes.presentation.ui.notes.home.HomeNotesTopAppBar
import com.soft.dailynotes.presentation.ui.utils.Constants.NOTES_ID
import com.soft.dailynotes.presentation.ui.utils.Constants.STOP_TIME_OUT_MILLIS
import com.soft.dailynotes.presentation.ui.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


object NotesDetailsDestination : NavDestination {
    override val route = R.string.details
    override val resId: Int
        get() = TODO("Not yet implemented")
    const val noteIdArg = NOTES_ID
    val routeWithArgs = "$route/{$noteIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteDetails(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {}

) {
    val notesViewModel: NoteViewModel = hiltViewModel()
    val isUpdate = notesViewModel.noteId > 0
    val context = LocalContext.current
    var dialogState by rememberSaveable { mutableStateOf(false) }
    val noteUiState: NotesUiState = notesViewModel.notesState.collectAsState().value
    val selectedColor by
    animateColorAsState(
        targetValue = if (noteUiState.isDark) Utils.colorsDark[noteUiState.color] else Utils.colors[noteUiState.color],
        animationSpec = tween(durationMillis = 500)
    )


    val icon = if (noteUiState.noteUpdatedStatus) Icons.Default.Refresh else Icons.Default.Check
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()


    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
        FloatingActionButton(onClick = {
            if (isUpdate) {
                scope.launch {
                    notesViewModel.updateNote()
                    delay(STOP_TIME_OUT_MILLIS)
                    onNavigateUp()


                }

            } else {
                scope.launch {
                    notesViewModel.addNote()
                    delay(STOP_TIME_OUT_MILLIS)
                    onNavigateUp()
                }
            }
        }, backgroundColor = MaterialTheme.colorScheme.primary) {
            Icon(imageVector = icon, contentDescription = null)
        }
    }, topBar = {
        HomeNotesTopAppBar(
            title = if (isUpdate) stringResource(
                id = R.string.details,
                noteUiState.title
            ) else stringResource(
                id = R.string.add_note
            ),
            isNavigateBack = true,
            navigateUp = { onNavigateUp() })
    }, bottomBar = {
        ButtonNav(showNotification = { dialogState = true })

    }, isFloatingActionButtonDocked = true) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly, contentPadding = PaddingValues(
                    horizontal = dimensionResource(
                        id = R.dimen.medium_padding
                    ), vertical = dimensionResource(id = R.dimen.large_padding)
                )
            ) {
                itemsIndexed(if (noteUiState.isDark) Utils.colorsDark else Utils.colors) { index, color ->
                    ColorItem(color = color, onClick = {
                        notesViewModel?.onColorChange(index)
                    })
                }
            }
            AnimatedVisibility(
                visible = noteUiState.noteUpdatedStatus,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                ConfirmationMessage(text = R.string.update_note_confirmation_message)
            }
            AnimatedVisibility(
                visible = noteUiState.noteAddedStatus,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                ConfirmationMessage(text = R.string.add_note_confirmation_message)
            }

            NotesEntries(
                noteUiState = noteUiState,
                selectedColor = selectedColor,
                onDescriptionChange = notesViewModel::onDescriptionChange,
                onTitleChange = notesViewModel::onTitleChange
            )


        }


    }
    if (dialogState) {
        ReminderDialog(
            onDismiss = { dialogState = false },
            reminderData = { notesViewModel.scheduleReminder(noteUiState.title, it) }, context
        )
    }


}




