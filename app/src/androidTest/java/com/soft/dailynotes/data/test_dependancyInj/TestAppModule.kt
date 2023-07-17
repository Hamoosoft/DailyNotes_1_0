package com.soft.dailynotes.data.test_dependancyInj

import android.content.Context
import androidx.room.Room
import com.soft.dailynotes.data.notedb.NotesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module

@InstallIn(SingletonComponent::class)

object TestAppModule {
    @Provides
    @Named("note_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, NotesDb::class.java).allowMainThreadQueries().build()


}