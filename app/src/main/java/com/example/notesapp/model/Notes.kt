package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Notes
constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var notes: String,
    var date: String,
    var priority: String
)