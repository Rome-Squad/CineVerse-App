package com.giraffe.movie.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.movie.utils.DatabaseConstants.MOVIE_TABLE


@Entity(tableName = MOVIE_TABLE)
data class MovieDto(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String,
    val description: String,
)
