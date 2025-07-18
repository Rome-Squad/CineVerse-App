package com.giraffe.media.series

import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.response.GenresResponse
import com.giraffe.media.series.response.SeriesResponse
import com.giraffe.media.series.response.SeriesReviewsResponse

class SeriesRemoteDataSourceImp(
    private val requestBuilder: com.giraffe.media.util.RequestBuilder
) : SeriesRemoteDataSource {
    override suspend fun getSeriesByName(name: String, page: Int) =
        requestBuilder.get<SeriesResponse>(
            endpoint = SEARCH_TV,
            params = mapOf(
                PAGE to page.toString(),
                QUERY to name
            )
        ).results

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int) =
        requestBuilder.get<SeriesResponse>(
            endpoint = DISCOVER_TV,
            params = mapOf(
                PAGE to page.toString(),
                WITH_GENRES_KEY to genreId.toString()
            )
        ).results

    override suspend fun getGenres() =
        requestBuilder.get<GenresResponse>(endpoint = GENRES_TV).genres

    override suspend fun getSeriesDetails(seriesId: Int) =
        requestBuilder.get<SeriesDetailsDto>(endpoint = "$TV/$seriesId")

    override suspend fun getSeriesReviews(seriesId: Int) =
        requestBuilder.get<SeriesReviewsResponse>(endpoint = "$TV/$seriesId/$REVIEWS").results

    override suspend fun getSeriesRecommendations(seriesId: Long, page: Int) =
        requestBuilder.get<SeriesResponse>(
            endpoint = "$TV/$seriesId/$RECOMMENDATIONS",
            params = mapOf(PAGE to page.toString())
        ).results

    companion object {
        private const val TV = "tv"
        private const val REVIEWS = "reviews"
        private const val SEARCH_TV = "search/tv"
        private const val DISCOVER_TV = "discover/tv"
        private const val GENRES_TV = "genre/tv/list"
        private const val QUERY = "query"
        private const val PAGE = "page"
        private const val WITH_GENRES_KEY = "withGenresKey"
        private const val RECOMMENDATIONS = "recommendations"
    }
}
