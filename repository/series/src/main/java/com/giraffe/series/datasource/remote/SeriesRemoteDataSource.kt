package com.giraffe.series.datasource.remote

import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeriesDto

interface SeriesRemoteDataSource {
    suspend fun getSeriesByName(name: String, language: String = "en-US", includeAdult: Boolean = false, page: Int = 1): List<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: Int, language: String = "en-US", includeAdult: Boolean = false, page: Int = 1, sortBy: String = "popularity.desc", includeNullFirstAirDates: Boolean = false): List<SeriesDto>
    suspend fun getGenres(language: String = "en-US"): List<GenreDto>
}