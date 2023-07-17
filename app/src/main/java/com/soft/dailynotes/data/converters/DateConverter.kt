package com.soft.dailynotes.data.converters

import androidx.room.TypeConverter
import java.util.Date

open class DataConverter{
    @TypeConverter
    fun toDate(date:Long?): Date?{
        return date?.let { Date(it) }

    }
    @TypeConverter
    fun toLong(date: Date?):Long?{
        return date?.time
    }

}