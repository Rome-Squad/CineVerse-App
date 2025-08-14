package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.MOVIE_TABLE


@Entity(tableName = MOVIE_TABLE)
data class MovieCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val youtubeVideoId: String?,
    val voteAverage: Float,
    val genresID: List<Int>,
    val releaseDate: String?,
    val duration: Int?,
    val popularity: Float,
)


