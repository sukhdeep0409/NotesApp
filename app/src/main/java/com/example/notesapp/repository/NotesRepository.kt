package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import com.example.notesapp.model.Notes
import com.example.notesapp.dao.NotesDao

class NotesRepository
constructor(private val dao: NotesDao){
    fun getAllNotes(): LiveData<List<Notes>> = dao.getNotes()

    fun insertNotes(notes: Notes) = dao.insertNote(notes)

    fun deleteNotes(id: Int) = dao.deleteNote(id)

    fun updateNotes(notes: Notes) = dao.updateNote(notes)
}