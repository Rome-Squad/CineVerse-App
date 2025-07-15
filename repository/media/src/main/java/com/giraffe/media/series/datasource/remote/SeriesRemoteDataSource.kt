package com.giraffe.media.series.datasource.remote

import com.giraffe.media.series.datasource.remote.response.seriesdetails.SeriesDetailsResponse
import com.giraffe.media.series.datasource.remote.response.seriesdetails.reviews.SeriesReviewsResponse
import com.giraffe.media.series.model.GenreDto
import com.giraffe.media.series.model.SeriesDto

interface SeriesRemoteDataSource {
    suspend fun getSeriesByName(name: String, page: Int = 1): List<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int = 1): List<SeriesDto>
    suspend fun getGenres(): List<GenreDto>
    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsResponse
    suspend fun getSeriesRecommendations(seriesId: Long, page: Int): List<SeriesDto>
    suspend fun getSeriesReviews(seriesId: Int): SeriesReviewsResponse
}