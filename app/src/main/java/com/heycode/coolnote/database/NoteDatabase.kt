package com.heycode.coolnote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.heycode.coolnote.models.NoteData
import com.heycode.coolnote.utlis.Converter
import com.heycode.coolnote.utlis.DATABASE_NAME

@Database(entities = [NoteData::class], version = 1)
@TypeConverters(Converter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDAO

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME,
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }

}