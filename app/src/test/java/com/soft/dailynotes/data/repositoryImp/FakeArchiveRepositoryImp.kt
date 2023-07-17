package com.soft.dailynotes.data.repositoryImp

import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.domain.repositories.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeArchiveRepositoryImp:ArchiveRepository {

    private val fakeArchiveDb = mutableListOf<Archive>()

    override suspend fun insetNote(note: Archive) {
       fakeArchiveDb.add(note)
    }

    override suspend fun updateNote(note: Archive) {

        fakeArchiveDb[0] = note
    }

    override suspend fun deleteNote(note: Archive) {

        fakeArchiveDb.remove(note)
    }

    override fun retrieveAllNotes(): Flow<List<Archive>> {

        return flow {  fakeArchiveDb }
    }

    override fun retrieveNote(noteId: Int): Flow<Archive> {

        return  flow {
            fakeArchiveDb.map { it.noteId == noteId }
        }
    }
}