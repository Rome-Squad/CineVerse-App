package com.giraffe.media.series

import com.giraffe.media.series.api.BaseRequest
import com.giraffe.media.series.api.RequestBuilder
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.response.seriesdetails.SeriesDetailsResponse
import com.giraffe.media.series.datasource.remote.response.seriesdetails.reviews.SeriesReviewsResponse
import com.giraffe.media.series.model.GenreDto
import com.giraffe.media.series.model.SeriesDto
import com.giraffe.media.series.model_dto.GenresResponse
import com.giraffe.media.series.model_dto.SeriesResponse
import io.ktor.client.call.body
import io.ktor.http.HttpMethod

class TmdbSeriesApiRemoteDataSource(
    val requestBuilder: RequestBuilder,
    val baseRequest: BaseRequest
) : SeriesRemoteDataSource {
    override suspend fun getSeriesByName(name: String, page: Int): List<SeriesDto> {
        val endpoint = "search/tv"
        val pageKey = "page"
        val queryKey = "query"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
            .addParameter(key = pageKey, value = page)
            .addParameter(key = queryKey, value = name)
        val response = requestBuilder.request(baseRequest).body<SeriesResponse>()
        return response.results

    }

    override suspend fun getSeriesByGenreId(genreId: Int, page: Int): List<SeriesDto> {
        val endpoint = "discover/tv"
        val pageKey = "page"
        val withGenresKey = "with_genres"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
            .addParameter(key = pageKey, value = page)
            .addParameter(key = withGenresKey, value = genreId)
        val response = requestBuilder.request(baseRequest).body<SeriesResponse>()
        return response.results
    }

    override suspend fun getGenres(): List<GenreDto> {
        val endpoint = "genre/tv/list"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
        val response = requestBuilder.request(baseRequest).body<GenresResponse>()
        return response.genres
    }

    override suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsResponse {
        val endpoint = "tv/$seriesId"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
        val result = requestBuilder.request(baseRequest).body<SeriesDetailsResponse>()
        return result
    }

    override suspend fun getSeriesReviews(seriesId: Int): SeriesReviewsResponse {
        val endPoint = "tv/$seriesId/reviews"
        baseRequest.endpoint(endPoint)
            .method(HttpMethod.Get)
        return requestBuilder.request(baseRequest).body<SeriesReviewsResponse>()
    }

    override suspend fun getSeriesRecommendations(seriesId: Long, page: Int): List<SeriesDto> {
        val endpoint = "tv/$seriesId/recommendations"
        return baseRequest
            .endpoint(endpoint)
            .method(HttpMethod.Get)
            .addParameter(key = "page", value = page)
            .run { requestBuilder.request(this).body<SeriesResponse>().results }
    }
}
