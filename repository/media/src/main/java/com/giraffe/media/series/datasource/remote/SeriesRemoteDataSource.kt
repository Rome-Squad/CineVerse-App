package com.giraffe.media.series.datasource.remote

import com.giraffe.media.series.datasource.remote.dto.ReviewDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.datasource.remote.dto.GenreDto

interface SeriesRemoteDataSource {
    suspend fun getSeriesByName(name: String, page: Int = 1): List<SeriesDto>
    suspend fun getSeriesByGenre(genreId: Int, page: Int = 1): List<SeriesDto>
    suspend fun getGenres(): List<GenreDto>
    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto
    suspend fun getSeriesRecommendations(seriesId: Long, page: Int): List<SeriesDto>
    suspend fun getSeriesReviews(seriesId: Int): List<ReviewDto>
}