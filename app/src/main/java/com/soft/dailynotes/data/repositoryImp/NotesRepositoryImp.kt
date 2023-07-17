package com.soft.dailynotes.data.repositoryImp

import com.soft.dailynotes.data.notedb.NotesDao
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImp @Inject constructor(private val notesDao: NotesDao): NotesRepository {
    override suspend fun insetNote(note: Notes) {
        notesDao.insetNote(note = note)
    }

    override suspend fun updateNote(note: Notes) {
        notesDao.updateNote(note = note)
    }

    override suspend fun deleteNote(note: Notes) {
       notesDao.deleteNote(note = note)
    }

    override fun retrieveAllNotes(): Flow<List<Notes>> {
      return notesDao.retrieveAllNotes()
    }

    override fun retrieveNote(noteId: Int): Flow<Notes> {
        return notesDao.retrieveNote(noteId)
    }

    override fun searchNotesByDateOrTitle(noteId: String): Flow<List<Notes>> {
       return notesDao.searchNotesByDateOrTitle(noteId)
    }


}