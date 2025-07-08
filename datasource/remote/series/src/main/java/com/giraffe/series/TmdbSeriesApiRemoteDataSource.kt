package com.giraffe.series

import com.giraffe.series.api.ApiResult
import com.giraffe.series.api.BaseRequest
import com.giraffe.series.api.RequestBuilder
import com.giraffe.series.model_dto.GenreDto
import com.giraffe.series.model_dto.GenresResponse
import com.giraffe.series.model_dto.SeriesDto
import com.giraffe.series.model_dto.SeriesInfoDto
import io.ktor.http.HttpMethod
import java.io.FileInputStream
import java.util.Properties


class TmdbSeriesApiRemoteDataSource(
    val requestBuilder: RequestBuilder,
    val baseRequest: BaseRequest
) : SeriesRemoteDataSource {
    private val authorizationKey = "Authorization"
    private val acceptKey = "accept"
    private val applicationJsonValue = "application/json"
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
            .addHeader(
                key = authorizationKey,
                value = "Bearer ${getApiKey()}"
            )
            .addHeader(key = acceptKey, value = applicationJsonValue)
        val response = requestBuilder.request<SeriesInfoDto>(baseRequest)
        return when (response) {
            is ApiResult.Error -> throw response.exception
            is ApiResult.Success -> response.data.results
        }

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
            .addHeader(
                key = authorizationKey,
                value = "Bearer ${getApiKey()}"
            )
            .addHeader(key = acceptKey, value = applicationJsonValue)
        val response = requestBuilder.request<SeriesInfoDto>(baseRequest)
        return when (response) {
            is ApiResult.Error -> throw response.exception
            is ApiResult.Success -> response.data.results
        }
    }

    override suspend fun getGenres(language: String): List<GenreDto> {
        val endpoint = "genre/tv/list"
        baseRequest.endpoint(endpoint)
            .method(HttpMethod.Get)
            .addParameter(key = languageKey, value = language)
            .addHeader(
                key = authorizationKey,
                value = "Bearer ${getApiKey()}"
            )
            .addHeader(key = acceptKey, value = applicationJsonValue)
        val response = requestBuilder.request<GenresResponse>(baseRequest)
        return when (response) {
            is ApiResult.Error -> throw response.exception
            is ApiResult.Success -> response.data.genres
        }
    }

    private fun getApiKey(): String {
        val properties = Properties().apply {
            load(FileInputStream("local.properties"))
        }
        return properties.getProperty("TMDB_API_KEY") ?: throw ApiKeyNotFoundException()
    }
}