package com.heycode.coolnote.utlis

import androidx.room.TypeConverter
import com.heycode.coolnote.models.Priority

class Converter {
    @TypeConverter
    fun fromPriorityToString(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun fromStringToPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}