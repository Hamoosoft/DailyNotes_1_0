package com.soft.dailynotes.presentation.ui.notes.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.repositories.NotesRepository
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import com.soft.dailynotes.domain.repositories.WorkerRepository
import com.soft.dailynotes.presentation.ui.utils.Constants.ADD_NOTE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository,
    private val preferenceRepository: PreferenceRepository,
    private val notificationRepository: WorkerRepository

) : ViewModel() {
    val noteId: Int = checkNotNull(savedStateHandle[NotesDetailsDestination.noteIdArg])
    private var _notesState = MutableStateFlow(NotesUiState())
    val notesState: StateFlow<NotesUiState> = _notesState.asStateFlow()

    fun onColorChange(color: Int) {
        _notesState.value = _notesState.value.copy(
            color = color
        )
    }

    fun onTitleChange(title: String) {
        _notesState.value = _notesState.value.copy(
            title = title
        )
    }

    fun onDescriptionChange(description: String) {
        _notesState.value = _notesState.value.copy(
            description = description
        )
    }

    fun scheduleReminder(title: String, duration: Long) {
        viewModelScope.launch {
            notificationRepository.applyReminder(duration = duration, title)
            _notesState.value = _notesState.value.copy(
                schedule = "schedule"
            )
        }
    }

    private fun getNote() {
        viewModelScope.launch {
            notesRepository.retrieveNote(noteId).collectLatest {
                _notesState.value = _notesState.value.copy(
                    color = it.color,
                    description = it.description,
                    title = it.title
                )

            }

        }
    }


    init {
        if (noteId != ADD_NOTE_KEY)
            getNote()
        resetNoteEditStatus()

    }


    fun addNote() {
        viewModelScope.launch {

            launch {
                notesRepository.insetNote(
                    Notes(
                        title = _notesState.value.title,
                        color = _notesState.value.color,
                        description = _notesState.value.description,
                        isDark = _notesState.value.isDark,
                        schedule = _notesState.value.schedule ?: ""
                    )
                )
                _notesState.value = _notesState.value.copy(noteAddedStatus = true)
            }

        }

    }

    fun updateNote() {
        if (noteId > 0) {
            viewModelScope.launch {
                notesRepository.updateNote(
                    Notes(
                        noteId = noteId,
                        title = _notesState.value.title,
                        color = _notesState.value.color,
                        description = _notesState.value.description,
                        isDark = _notesState.value.isDark,
                        schedule = _notesState.value.schedule ?: ""
                    )
                )
                _notesState.value = _notesState.value.copy(noteUpdatedStatus = true)
            }
        }

    }




    private fun resetNoteEditStatus() {
        _notesState.value =
            _notesState.value.copy(noteUpdatedStatus = false, noteAddedStatus = false)
    }



    init {
        viewModelScope.launch {
            preferenceRepository.isDark.collect {
                _notesState.value = _notesState.value.copy(
                    isDark = it
                )
            }
        }
    }
}

data class NotesUiState(
    val color: Int = 0,
    val title: String = "",
    val description: String = "",
    val noteAddedStatus: Boolean = false,
    val noteUpdatedStatus: Boolean = false,
    val selectedNote: Notes? = null,
    val schedule: String? = null,
    val isDark: Boolean = false
)

//data class Error(val throwable: Throwable)

