package com.heycode.coolnote.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.heycode.coolnote.utlis.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class NoteData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val priority: Priority
) {

}