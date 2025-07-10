package com.giraffe.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.movie.datasource.local.cacheDto.MovieDto.Companion.MOVIE_TABLE
import kotlinx.datetime.LocalDate



@Entity(tableName = MOVIE_TABLE)
data class MovieDto(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath : String,
    val voteAverage : Float,
    val genresID: List<Int>,
    val releaseDate : LocalDate
){
    companion object {
        const val MOVIE_TABLE = "movie_table"
    }
}



