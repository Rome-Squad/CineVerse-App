package com.giraffe.movie.dto

import androidx.room.Entity
import com.giraffe.movie.utils.DatabaseConstants.MOVIE_TABLE


@Entity(tableName = MOVIE_TABLE)
data class MovieDto(
    val id: Long,
    val name: String,
    val description: String,
)
