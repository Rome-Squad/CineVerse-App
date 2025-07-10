package com.giraffe.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreDto.Companion.MOVIE_GENRE_TABLE


@Entity(tableName = MOVIE_GENRE_TABLE)
data class MovieGenreDto(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String
){
    companion object {
        const val MOVIE_GENRE_TABLE = "movie_genre_table"
    }
}

