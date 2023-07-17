package com.soft.dailynotes.presentation.ui.notes.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.repositories.ArchiveRepository
import com.soft.dailynotes.domain.repositories.NotesRepository
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import com.soft.dailynotes.domain.repositories.WorkerRepository
import com.soft.dailynotes.presentation.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val archiveRepository: ArchiveRepository,
    private val notificationRepository: WorkerRepository,
    private val notesRepository: NotesRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {
    private val _archiveUiState = MutableStateFlow(ArchiveNotesUiState())
    val archive: StateFlow<ArchiveNotesUiState> = _archiveUiState.asStateFlow()
    fun deleteNote(notes: Archive) {
        viewModelScope.launch {
            archiveRepository.deleteNote(notes)
        }
    }

    fun safeLayoutState(isGridLayout: Boolean) {
        viewModelScope.launch {
            preferenceRepository.saveArchiveLayoutPreference(isGridLayout)
        }
    }


    fun getLayoutStatus(): Boolean {
        viewModelScope.launch {
            preferenceRepository.isArchiveLayoutGrit.collectLatest {
                _archiveUiState.value = _archiveUiState.value.copy(isArchiveGridLayout = it)
            }
        }
        return _archiveUiState.value.isArchiveGridLayout
    }

    fun scheduleReminder(title: String, duration: Long, noteId: Int) {
        viewModelScope.launch {
            notificationRepository.applyReminder(title = title, duration = duration)

        }
    }

    fun restoreNoteToNotesRepository(note: Archive) {
        viewModelScope.launch {
            notesRepository.insetNote(note.toNote())
            archiveRepository.deleteNote(note)
        }


    }

    fun onSearchChanged(text: String) {
        _searchText.value = text

    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val searchUiState: StateFlow<List<Archive>> =
        searchText
            .combine(_archiveUiState) { text, notes ->
                if (text.isBlank()) {
                    notes.notes
                } else {
                    notes.notes.filter {
                        it.title.contains(text)

                    }
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(Constants.STOP_TIME_OUT_MILLIS),
                ArchiveNotesUiState().notes
            )

    init {
        viewModelScope.launch {
            archiveRepository.retrieveAllNotes().collectLatest {
                _archiveUiState.value = _archiveUiState.value.copy(
                    notes = it
                )
            }
        }
    }

}

data class ArchiveNotesUiState(
    val notes: List<Archive> = emptyList(),
    val isDarkMode: Boolean = false,
    val isArchiveGridLayout: Boolean = false,
    var archive: Archive = Archive(
        title = "",
        description = "",
        date = Date(),
        color = 0,
        isDark = false,
        schedule = ""
    )


)

fun Archive.toNote(): Notes {
    return Notes(
        noteId = this.noteId,
        title = this.title,
        schedule = this.schedule,
        description = this.description,
        date = this.date,
        color = this.color,
        isDark = this.isDark
    )
}