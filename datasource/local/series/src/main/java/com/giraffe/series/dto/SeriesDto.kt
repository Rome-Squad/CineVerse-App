package com.giraffe.series.dto


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class SeriesEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val rate: Float,
    val duration: String,
    val posterUrl: String,
    val genresID: List<Int>,
    val releaseYear: String,
)


@Entity(tableName = "seasons")
data class SeasonEntity(
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


@Entity(tableName = "series_genres")
data class SeriesGenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)