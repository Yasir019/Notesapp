package com.yasir4y.notesapp.repository
import androidx.room.Query
import com.yasir4y.notesapp.database.NoteDatabase
import com.yasir4y.notesapp.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insertNote(note:Note) = db.getNoteDao().inserNote(note)
    suspend fun deleteNote(note:Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note:Note) = db.getNoteDao().updateNote(note)

    fun getAllNote() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)

}