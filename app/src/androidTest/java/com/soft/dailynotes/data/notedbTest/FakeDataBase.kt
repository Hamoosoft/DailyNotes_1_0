package com.soft.dailynotes.data.notedbTest

import com.soft.dailynotes.domain.models.Notes
import java.util.Date

object FakeDataBase {
    val notes = listOf(
        Notes(
            noteId = 1,
            title = "remember",
            schedule = "schedule",
            description = "notes description",
            date = Date(),
            color = 1,
            isDark = false
        ),
        Notes(
            noteId = 2,
            title = "remember2",
            schedule = "schedule2",
            description = "notes description2",
            date = Date(),
            color = 4,
            isDark = false
        ),Notes(
            noteId = 3,
            title = "remember3",
            schedule = "schedule3",
            description = "notes description3",
            date = Date(),
            color = 2,
            isDark = false
        ),Notes(
            noteId = 1,
            title = "remember3",
            schedule = "schedule3",
            description = "notes description3",
            date = Date(),
            color = 5,
            isDark = false
        )

    )
}