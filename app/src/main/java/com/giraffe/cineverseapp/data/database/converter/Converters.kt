package com.giraffe.cineverseapp.data.database.converter

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromList(value: List<Int>?): String {
        return value?.joinToString(",") .orEmpty()
    }

    @TypeConverter
    fun toList(value: String): List<Int> {
        return if (value.isEmpty()) emptyList() else value.split(",").map { it.toInt() }
    }
}
