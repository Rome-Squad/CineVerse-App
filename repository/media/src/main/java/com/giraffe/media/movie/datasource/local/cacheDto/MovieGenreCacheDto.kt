package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto.Companion.MOVIE_GENRE_TABLE


@Entity(tableName = MOVIE_GENRE_TABLE)
data class MovieGenreCacheDto(

    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val name : String
){
    companion object {
        const val MOVIE_GENRE_TABLE = "movie_genre_table"
    }
}