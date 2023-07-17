package com.soft.dailynotes.domain.repositories

import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.domain.models.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insetNote(note: Notes)
    suspend fun updateNote(note: Notes)
    suspend fun deleteNote(note: Notes)
    fun retrieveAllNotes(): Flow<List<Notes>>
    fun retrieveNote(noteId: Int): Flow<Notes>
    fun searchNotesByDateOrTitle(noteId: String): Flow<List<Notes>>

}

interface ArchiveRepository {
    suspend fun insetNote(note: Archive)
    suspend fun updateNote(note: Archive)
    suspend fun deleteNote(note: Archive)
    fun retrieveAllNotes(): Flow<List<Archive>>
    fun retrieveNote(noteId: Int): Flow<Archive>

}

