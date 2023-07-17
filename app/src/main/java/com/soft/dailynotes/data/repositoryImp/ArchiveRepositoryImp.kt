package com.soft.dailynotes.data.repositoryImp

import com.soft.dailynotes.data.notedb.ArchiveDao
import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.domain.repositories.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveRepositoryImp @Inject constructor(private val archiveDao: ArchiveDao): ArchiveRepository {

    override suspend fun insetNote(note: Archive) {
       archiveDao.insetNote(note)
    }

    override suspend fun updateNote(note: Archive) {
        archiveDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Archive) {
       archiveDao.deleteNote(note)
    }

    override fun retrieveAllNotes(): Flow<List<Archive>> {
      return archiveDao.retrieveAllNotes()
    }

    override fun retrieveNote(noteId: Int): Flow<Archive> {
       return archiveDao.retrieveNote(noteId)
    }
}