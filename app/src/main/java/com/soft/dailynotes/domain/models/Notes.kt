package com.soft.dailynotes.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "note")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    val title: String,
    val schedule: String,
    val description: String,
    val date: Date = Date(),
    val color: Int,
    val isDark: Boolean
)

@Entity(tableName = "archive")
data class Archive(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    val title: String,
    val schedule: String,
    val description: String,
    val date: Date = Date(),
    val color: Int,
    val isDark: Boolean
)

@Entity(tableName = "setting")
data class Settings(
    @PrimaryKey()
    val Id: Int = 1,
    val isEnglish: Boolean,
    val isDark: Boolean,

    )
