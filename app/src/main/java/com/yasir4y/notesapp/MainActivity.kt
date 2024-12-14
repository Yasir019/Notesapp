package com.yasir4y.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yasir4y.notesapp.database.NoteDatabase
import com.yasir4y.notesapp.repository.NoteRepository
import com.yasir4y.notesapp.viewmodel.NoteViewModel
import com.yasir4y.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        // Get an instance of the database
        val noteDatabase = NoteDatabase.invoke(this)
        // Pass the database to the repository
        val noteRepository = NoteRepository(noteDatabase)
        // Create the ViewModelFactory with the application context and repository
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
        // Initialize the ViewModel using the factory
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}
