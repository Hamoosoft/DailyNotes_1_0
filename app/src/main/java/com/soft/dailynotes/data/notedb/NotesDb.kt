package com.soft.dailynotes.data.notedb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soft.dailynotes.data.converters.DataConverter
import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.models.Settings

@TypeConverters(value = [DataConverter::class])
@Database(entities = [Notes::class, Settings::class, Archive::class], version = 1, exportSchema = false)
abstract class NotesDb:RoomDatabase(){
    abstract fun notesDao(): NotesDao
    abstract fun archiveDao(): ArchiveDao
    abstract fun settingDao(): SettingsDao

    }

