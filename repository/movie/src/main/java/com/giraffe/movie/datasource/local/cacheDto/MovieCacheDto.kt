package com.giraffe.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto.Companion.MOVIE_TABLE


@Entity(tableName = MOVIE_TABLE)
data class MovieCacheDto(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath : String?,
    val voteAverage : Float,
    val genresID: List<Int>,
    val releaseDate : String?,
    val duration : Int?,
    val isRecent : Boolean,
    val cachedAt: Long = System.currentTimeMillis()
){
    companion object {
        const val MOVIE_TABLE = "movies"
    }
}



