package com.soft.dailynotes.data.repositoryImp

import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNotesRepository:NotesRepository {

    private val fakeNotesDb = mutableListOf<Notes>()


    override suspend fun insetNote(note: Notes) {
        fakeNotesDb.add(note)
    }

    override suspend fun updateNote(note: Notes) {
        fakeNotesDb[0] = note
    }

    override suspend fun deleteNote(note: Notes) {
        fakeNotesDb.remove(note)
    }

    override fun retrieveAllNotes(): Flow<List<Notes>> {
        return  flow {
            fakeNotesDb
        }
    }

    override fun retrieveNote(noteId: Int): Flow<Notes> {
        return  flow {
            fakeNotesDb.map { it.noteId == noteId }
        }
    }

    override fun searchNotesByDateOrTitle(noteId: String): Flow<List<Notes>> {
        return  flow {
            fakeNotesDb.map { it.title == noteId }
        }
    }
}