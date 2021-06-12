package com.heycode.coolnote.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.heycode.coolnote.utlis.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class NoteData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val priority: Priority
) : Parcelable {

}