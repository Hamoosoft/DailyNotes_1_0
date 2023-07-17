package com.soft.dailynotes.data.repositoryImp.notesorder

import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface NotesOperatorsPlan {
    fun getNotesOrder(notesOrderBy: NotesOrderBy, noteOrderType: NoteOrderType): Flow<List<Notes>>
}

class NotesOperator @Inject constructor(private val notesRepository: NotesRepository) : NotesOperatorsPlan {

    operator fun invoke(
        notesOrderBy: NotesOrderBy,
        noteOrderType: NoteOrderType
    ): Flow<List<Notes>> {
        return notesRepository.retrieveAllNotes().map { notes ->
            when (noteOrderType) {
                is NoteOrderType.Ascending -> {
                    when (notesOrderBy) {
                        is NotesOrderBy.Date -> notes.sortedBy { it.date }
                        is NotesOrderBy.Color -> notes.sortedBy { it.color }
                        is NotesOrderBy.Title -> notes.sortedBy { it.title }
                    }
                }

                is NoteOrderType.Descending -> {

                    when (notesOrderBy) {
                        is NotesOrderBy.Date -> notes.sortedByDescending { it.date }
                        is NotesOrderBy.Color -> notes.sortedByDescending { it.color }
                        is NotesOrderBy.Title -> notes.sortedByDescending { it.title }
                    }
                }
            }
        }

    }

    override fun getNotesOrder(
        notesOrderBy: NotesOrderBy,
        noteOrderType: NoteOrderType
    ): Flow<List<Notes>> {
        return invoke(notesOrderBy = notesOrderBy, noteOrderType = noteOrderType)
    }


}