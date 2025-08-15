package com.giraffe.media.movie.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.MOVIE_TABLE


@Entity(tableName = MOVIE_TABLE)
data class MovieCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val youtubeVideoId: String?,
    val rating: Float,
    val genresID: List<Int>,
    val releaseYear: String?,
    val duration: Int?,
    val popularity: Float,
)


