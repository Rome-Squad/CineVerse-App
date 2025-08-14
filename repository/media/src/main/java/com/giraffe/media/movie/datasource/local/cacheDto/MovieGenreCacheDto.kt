package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.MOVIE_GENRE_TABLE


@Entity(tableName = MOVIE_GENRE_TABLE)
data class MovieGenreCacheDto(

    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val name : String,
    val language: String,
    val count: Int = 0
)