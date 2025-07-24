package com.giraffe.media.series.datasource.remote

import com.giraffe.media.series.datasource.remote.dto.GenreDto
import com.giraffe.media.series.datasource.remote.dto.ReviewDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto

interface SeriesRemoteDataSource {
    suspend fun getSeriesByName(name: String, page: Int = 1): List<SeriesDto>
    suspend fun getSeriesByGenre(genreId: Int, page: Int = 1): List<SeriesDto>
    suspend fun getGenres(): List<GenreDto>
    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto
    suspend fun getSeriesRecommendations(seriesId: Long, page: Int): List<SeriesDto>
    suspend fun getSeriesReviews(seriesId: Int, page: Int = 1): List<ReviewDto>
    suspend fun getPopularitySeries(page: Int): List<SeriesDto>
    suspend fun getRecentlyReleasedSeries(page: Int): List<SeriesDto>
    suspend fun getTopRatedSeries(page: Int): List<SeriesDto>
}