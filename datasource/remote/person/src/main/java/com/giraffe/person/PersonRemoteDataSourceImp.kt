package com.giraffe.person

import com.giraffe.person.remote.PersonRemoteDataSource
import com.giraffe.person.remote.response.CreditsResponse
import com.giraffe.person.remote.response.SearchPersonResponse
import com.giraffe.person.util.RequestBuilder
import io.ktor.client.HttpClient

class PersonRemoteDataSourceImp(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val accessToken: String,
) : PersonRemoteDataSource {
    override suspend fun searchByName(personName: String): SearchPersonResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = SEARCH_PERSON_END_POINT,
                params = mapOf(
                    QUERY to personName,
                    INCLUDE_ADULT to "false",
                    LANGUAGE to "en-US",
                    PAGE to "1"
                )
            )
    }

    override suspend fun getCreditsByShowId(showId: Int): CreditsResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = "$CREDITS_SHOW_END_POINT/$showId/$CREDITS",
                params = mapOf(LANGUAGE to "en-US")
            )
    }

    override suspend fun getCreditsByMovieId(movieId: Int): CreditsResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = "$CREDITS_MOVIE_END_POINT/$movieId/$CREDITS",
                params = mapOf(LANGUAGE to "en-US")
            )
    }


    companion object {
        private const val CREDITS_MOVIE_END_POINT = "movie"
        private const val CREDITS = "credits"
        private const val CREDITS_SHOW_END_POINT = "tv"
        private const val SEARCH_PERSON_END_POINT = "search/person"
        private const val QUERY = "query"
        private const val INCLUDE_ADULT = "include_adult"
        private const val LANGUAGE = "language"
        private const val PAGE = "page"
    }
}

