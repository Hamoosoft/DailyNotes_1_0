package com.soft.dailynotes.dependency_inj

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.soft.dailynotes.data.notedb.ArchiveDao
import com.soft.dailynotes.data.notedb.NotesDao
import com.soft.dailynotes.data.notedb.NotesDb
import com.soft.dailynotes.data.repositoryImp.ArchiveRepositoryImp
import com.soft.dailynotes.data.repositoryImp.LangPreferenceRepository
import com.soft.dailynotes.data.repositoryImp.NotesRepositoryImp
import com.soft.dailynotes.data.repositoryImp.WorkerRepositoryImp
import com.soft.dailynotes.data.repositoryImp.notesorder.NotesOperator
import com.soft.dailynotes.data.repositoryImp.notesorder.NotesOperatorsPlan
import com.soft.dailynotes.domain.repositories.ArchiveRepository
import com.soft.dailynotes.domain.repositories.NotesRepository
import com.soft.dailynotes.domain.repositories.PreferenceRepository
import com.soft.dailynotes.domain.repositories.WorkerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val LANGUAGE_PREFERENCE_NAME = "English_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LANGUAGE_PREFERENCE_NAME
)

@Module
@InstallIn(SingletonComponent::class)
object DefaultContainer {


    @Provides
    @Singleton
    fun provideNotesDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NotesDb::class.java, "notes_db").allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun provideNotesDao(notesDb: NotesDb) = notesDb.notesDao()

    @Provides
    @Singleton
    fun provideArchiveDao(notesDb: NotesDb) = notesDb.archiveDao()

    @Provides
    @Singleton
    fun provideArchiveRepository(archiveDao: ArchiveDao): ArchiveRepository {
        return ArchiveRepositoryImp(archiveDao = archiveDao)
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepositoryImp(notesDao = notesDao)
    }

    @Provides
    @Singleton
    fun providePreference(dataStore: DataStore<Preferences>): PreferenceRepository {
        return LangPreferenceRepository(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideWorkerRepository(@ApplicationContext context: Context): WorkerRepository {
        return WorkerRepositoryImp(context = context)
    }

    @Provides
    @Singleton
    fun notesOperator(notesRepository: NotesRepository): NotesOperatorsPlan {
        return NotesOperator(notesRepository = notesRepository)
    }


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore


}