package com.giraffe.media.movie.model.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.MOVIE_GENRE_TABLE


@Entity(tableName = MOVIE_GENRE_TABLE)
data class MovieGenreCacheDto(

    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val name : String
)