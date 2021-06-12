package com.heycode.coolnote.database

import androidx.lifecycle.LiveData
import com.heycode.coolnote.models.NoteData

class NoteRepository(private val noteDao: NoteDAO) {
    fun getAllNotes(): LiveData<List<NoteData>> = noteDao.getAllNotes()

    suspend fun insertNote(noteData: NoteData) = noteDao.insertNote(noteData)

    suspend fun updateNote(noteData: NoteData) = noteDao.updateNote(noteData)

    suspend fun deleteNote(noteData: NoteData) = noteDao.updateNote(noteData)
}