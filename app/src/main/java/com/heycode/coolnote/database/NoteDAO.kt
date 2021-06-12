package com.heycode.coolnote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.heycode.coolnote.models.NoteData

@Dao
interface NoteDAO {

    @Query("SELECT * from note_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<NoteData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(noteData: NoteData)

    @Update
    suspend fun updateNote(noteData: NoteData)

    @Delete
    suspend fun deleteNote(noteData: NoteData)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()
}