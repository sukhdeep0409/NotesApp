package com.example.notesapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.model.Notes
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.repository.NotesRepository

class NotesViewModel
constructor(application: Application):
AndroidViewModel(application) {
    private val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes: Notes) = repository.insertNotes(notes)

    fun getNotes(): LiveData<List<Notes>> = repository.getAllNotes()

    fun deleteNotes(id: Int) = repository.deleteNotes(id)

    fun updateNotes(notes: Notes) = repository.updateNotes(notes)
}