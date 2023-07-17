package com.soft.dailynotes.presentation.ui.notes.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soft.dailynotes.data.repositoryImp.notesorder.NoteOrderType
import com.soft.dailynotes.data.repositoryImp.notesorder.NotesOperatorsPlan
import com.soft.dailynotes.data.repositoryImp.notesorder.NotesOrderBy
import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.repositories.ArchiveRepository
import com.soft.dailynotes.domain.repositories.NotesRepository
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import com.soft.dailynotes.domain.repositories.WorkerRepository
import com.soft.dailynotes.presentation.ui.utils.Constants.STOP_TIME_OUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
     val notesRepository: NotesRepository,
    private val notificationRepository: WorkerRepository,
    private val archiveRepository: ArchiveRepository,
    private val notesOperatorsPlan: NotesOperatorsPlan,
    val preferenceRepository: PreferenceRepository
) : ViewModel() {
     var _homeUiState = MutableStateFlow(HomeNotesUiState())
    var homeUiState: StateFlow<HomeNotesUiState> = _homeUiState.asStateFlow()


    fun getLayoutStatus():Boolean{
        viewModelScope.launch {
            preferenceRepository.isHomeLayoutGrit.collectLatest {
                _homeUiState.value = _homeUiState.value.copy(isHome = it)
            }
        }
        return _homeUiState.value.isHome
    }

    fun saveLayoutStatus(){
        viewModelScope.launch{
            preferenceRepository.saveHomeLayoutPreference(!_homeUiState.value.isHome)
        }


    }

    fun darkMode(): Boolean {
        viewModelScope.launch {
            preferenceRepository.isDark.collectLatest {
                _homeUiState.value = _homeUiState.value.copy(isDarkMode = it)
            }
        }
        return _homeUiState.value.isDarkMode
    }

    fun orderBy(
        notesOrderBy: NotesOrderBy,
        noteOrderType: NoteOrderType
    ) {
        _homeUiState.value = _homeUiState.value.copy(
            notesOrderBy = notesOrderBy,
            notesOrderType = noteOrderType
        )

        viewModelScope.launch {
            notesOperatorsPlan.getNotesOrder(
                notesOrderBy = notesOrderBy,
                noteOrderType = noteOrderType
            ).collectLatest {
                _homeUiState.value = _homeUiState.value.copy(notes = it)
            }

        }


    }

    init {
        orderBy(
            noteOrderType = _homeUiState.value.notesOrderType,
            notesOrderBy = _homeUiState.value.notesOrderBy
        )

    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)

    val searchUiState: StateFlow<List<Notes>> =
        searchText
            .debounce(500)
            .onEach { _isSearching.update { true } }
            .combine(_homeUiState) { text, notes ->
                if (text.isBlank()) {
                    notes.notes
                } else {
                    notes.notes.filter {
                        it.title.contains(text)

                    }
                }
            }.onEach {
                _isSearching.update { false }

            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLIS),
                HomeNotesUiState().notes
            )

    fun deleteNote(notes: Notes) {
        viewModelScope.launch {
            notesRepository.deleteNote(notes)
        }
    }

    fun onSearchChanged(text: String) {
        _searchText.value = text

    }

    fun onArchiveRequest(note: Notes) {
        viewModelScope.launch {
            archiveRepository.insetNote(note.toArchive())
            notesRepository.deleteNote(note)
        }
    }

    fun scheduleReminder(title: String, duration: Long) {
        viewModelScope.launch {
            notificationRepository.applyReminder(title = title, duration = duration)
        }
    }


}

data class HomeNotesUiState(
    val notes: List<Notes> = emptyList(),
    val isDarkMode: Boolean = false,
    val isHome: Boolean = false,
    val notesOrderBy: NotesOrderBy = NotesOrderBy.Date,
    val notesOrderType: NoteOrderType = NoteOrderType.Ascending


)


fun Notes.toArchive(): Archive {
    return Archive(
        noteId = this.noteId,
        title = this.title,
        schedule = this.schedule,
        description = this.description,
        date = this.date,
        color = this.color,
        isDark = this.isDark
    )
}

