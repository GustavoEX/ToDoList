package com.example.gustavorodrigues.todolist.data.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

class dateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}