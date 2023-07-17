package com.soft.dailynotes.data.repositoryImp

import com.soft.dailynotes.data.repositoryImp.notesorder.NoteOrderType
import com.soft.dailynotes.data.repositoryImp.notesorder.NotesOperatorsPlan
import com.soft.dailynotes.data.repositoryImp.notesorder.NotesOrderBy
import com.soft.dailynotes.domain.models.Notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNotesOperator: NotesOperatorsPlan {
    override fun getNotesOrder(
        notesOrderBy: NotesOrderBy,
        noteOrderType: NoteOrderType
    ): Flow<List<Notes>> {
       return flow {
           emptyList<Notes>()
       }
    }
}