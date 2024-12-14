package com.yasir4y.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yasir4y.notesapp.model.Note
import com.yasir4y.notesapp.model.NoteDao

@Database(entities = [Note::class], version = 1, exportSchema = false) // Added exportSchema for clarity
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): NoteDatabase = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context): NoteDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db" // Fixed the "name" parameter usage
            ).build()
    }
}
