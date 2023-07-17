package com.soft.dailynotes.data.notedb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.soft.dailynotes.domain.models.Archive
import com.soft.dailynotes.domain.models.Notes
import com.soft.dailynotes.domain.models.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetNote(note: Notes)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Notes)
    @Delete
    suspend fun deleteNote(note: Notes)
    @Query("SELECT * FROM note")
    fun retrieveAllNotes():Flow<List<Notes>>
    @Query("SELECT * FROM note where noteId =:noteId")
    fun retrieveNote(noteId:Int):Flow<Notes>
    @Query("SELECT * FROM note where title LIKE '%' || :noteId || '%' order by date DESC")
    fun searchNotesByDateOrTitle(noteId:String):Flow<List<Notes>>
}
@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSetting(settings: Settings)
    @Query("SELECT * FROM setting where Id =1 ")
    fun retrieveSetting():Flow<Settings>
}
@Dao
interface ArchiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetNote(note: Archive)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Archive)
    @Delete
    suspend fun deleteNote(note: Archive)
    @Query("SELECT * FROM archive")
    fun retrieveAllNotes():Flow<List<Archive>>
    @Query("SELECT * FROM archive where noteId =:noteId")
    fun retrieveNote(noteId:Int):Flow<Archive>
}