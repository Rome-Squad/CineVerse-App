package com.giraffe.series.datasource.remote

import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeriesDto

interface SeriesRemoteDataSource {
    suspend fun getSeriesByName(name: String, page: Int = 1): List<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int = 1): List<SeriesDto>
    suspend fun getGenres(): List<GenreDto>
}