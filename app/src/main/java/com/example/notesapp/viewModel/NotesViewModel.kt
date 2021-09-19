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

    fun getLowNotes(): LiveData<List<Notes>> = repository.getLowNotes()

    fun getMediumNotes(): LiveData<List<Notes>> = repository.getMediumNotes()

    fun getHighNotes(): LiveData<List<Notes>> = repository.getHighNotes()

    fun getNotes(): LiveData<List<Notes>> = repository.getAllNotes()

    fun deleteNotes(id: Int) = repository.deleteNotes(id)

    fun updateNotes(notes: Notes) = repository.updateNotes(notes)
}