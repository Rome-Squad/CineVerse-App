package  com.giraffe.media.movie.converter

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenresIdList(value: List<Int>?): String {
        return value?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toGenresIdList(value: String): List<Int> {
        return if (value.isEmpty()) emptyList() else value.split(",").map { it.toInt() }
    }
}
