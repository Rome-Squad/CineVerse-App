package com.giraffe.series.datasource.remote

import com.giraffe.series.datasource.remote.response.seriesdetails.reviews.SeriesReviewsResponse
import com.giraffe.series.datasource.remote.response.seriesdetails.SeriesDetailsResponse
import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeriesDto

interface SeriesRemoteDataSource {
    suspend fun getSeriesByName(name: String, page: Int = 1): List<SeriesDto>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int = 1): List<SeriesDto>
    suspend fun getGenres(): List<GenreDto>
    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsResponse
    suspend fun getSeriesReviews(seriesId: Int): SeriesReviewsResponse
}