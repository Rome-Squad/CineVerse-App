package com.giraffe.series.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.series.util.DatabaseConstants.SERIES_TABLE
import com.giraffe.series.util.DatabaseConstants.SEASON_TABLE
import com.giraffe.series.util.DatabaseConstants.GENRE_TABLE

@Entity(tableName = SERIES_TABLE)
data class CachedSeriesDto(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val duration: String,
    val posterUrl: String,
    val genresID: List<Int>,
    val releaseYear: String,
)


@Entity(tableName = SEASON_TABLE)
data class CachedSeasonDto(
    @PrimaryKey val id: Int,
    val seriesId: Int, // foreign key
    val name: String,
    val overview: String,
    val rate: Float,
    val posterUrl: String,
    val seasonNumber: Int,
    val releaseYear: String,
    val numberOfEpisodes: Int
)


@Entity(tableName = GENRE_TABLE)
data class CachedSeriesGenreDto(
    @PrimaryKey val id: Int,
    val name: String
)