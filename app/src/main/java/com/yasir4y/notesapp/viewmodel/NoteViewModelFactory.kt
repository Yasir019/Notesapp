package com.yasir4y.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasir4y.notesapp.repository.NoteRepository

class NoteViewModelFactory(
    private val app: Application,
    private val noteRepository: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(app, noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
