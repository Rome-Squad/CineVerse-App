package com.giraffe.series

import com.giraffe.series.api.BaseRequest
import com.giraffe.series.api.RequestBuilder
import com.giraffe.series.model_dto.GenreDto
import com.giraffe.series.model_dto.GenresResponse
import com.giraffe.series.model_dto.SeriesDto
import com.giraffe.series.model_dto.SeriesResponse
import io.ktor.http.HttpMethod


internal class TmdbSeriesApiRemoteDataSource(
    val requestBuilder: RequestBuilder,
    val baseRequest: BaseRequest
) : SeriesRemoteDataSource {
    private val languageKey = "language"

    override suspend fun getSeriesByName(
        name: String,
        language: String,
        includeAdult: Boolean,
        page: Int
    ): List<SeriesDto> {
        val endpoint = "search/tv"
        val includeAdultKey = "include_adult"
        val pageKey = "page"
        val queryKey = "query"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
            .addParameter(key = includeAdultKey, value = includeAdult)
            .addParameter(key = languageKey, value = language)
            .addParameter(key = pageKey, value = page)
            .addParameter(key = queryKey, value = name)
        val response = requestBuilder.request<SeriesResponse>(baseRequest)
        return response.results

    }

    override suspend fun getSeriesByGenreId(
        genreId: String,
        language: String,
        includeAdult: Boolean,
        page: Int,
        sortBy: String,
        includeNullFirstAirDates: Boolean
    ): List<SeriesDto> {
        val endpoint = "discover/tv"
        val includeAdultKey = "include_adult"
        val pageKey = "page"
        val withGenresKey = "with_genres"
        val includeNullFirstAirDatesKey = "include_null_first_air_dates"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
            .addParameter(key = includeAdultKey, value = includeAdult)
            .addParameter(key = languageKey, value = language)
            .addParameter(key = pageKey, value = page)
            .addParameter(key = withGenresKey, value = genreId)
            .addParameter(key = includeNullFirstAirDatesKey, value = includeNullFirstAirDates)
        val response = requestBuilder.request<SeriesResponse>(baseRequest)
        return response.results
    }

    override suspend fun getGenres(language: String): List<GenreDto> {
        val endpoint = "genre/tv/list"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
            .addParameter(key = languageKey, value = language)
        val response = requestBuilder.request<GenresResponse>(baseRequest)
        return response.genres
    }
}