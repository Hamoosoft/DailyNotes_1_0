package com.soft.dailynotes.data.repositoryImp.notesorder

sealed class NoteOrderType{
    object Ascending:NoteOrderType()
    object Descending:NoteOrderType()
}
sealed class NotesOrderBy(){
    object Date:NotesOrderBy()
    object Title:NotesOrderBy()
    object Color:NotesOrderBy()

}