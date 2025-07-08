package com.giraffe.series

import com.giraffe.series.model_dto.GenreDto
import com.giraffe.series.model_dto.SeriesDto

interface SeriesRemoteDataSource {
    suspend fun getSeriesByName(name: String, language: String = "en-US", includeAdult: Boolean = false, page: Int = 1): List<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: String, language: String = "en-US", includeAdult: Boolean = false, page: Int = 1, sortBy: String = "popularity.desc", includeNullFirstAirDates: Boolean = false): List<SeriesDto>
    suspend fun getGenres(language: String = "en-US"): List<GenreDto>
}