package com.heycode.coolnote.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.heycode.coolnote.database.NoteDatabase
import com.heycode.coolnote.database.NoteRepository
import com.heycode.coolnote.models.NoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao = NoteDatabase.getDatabase(application).getNoteDao()
    private val repository: NoteRepository = NoteRepository(noteDao)

    private val getAllNotes: LiveData<List<NoteData>> = repository.getAllNotes()

    fun insertNote(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(noteData)
        }
    }

}