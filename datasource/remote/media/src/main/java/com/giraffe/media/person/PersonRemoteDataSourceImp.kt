package com.giraffe.media.person

import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.model.dto.CreditsResponse
import com.giraffe.media.person.model.dto.PersonDetailsResponse
import com.giraffe.media.person.model.dto.PersonMovieCreditsResponse
import com.giraffe.media.person.model.dto.PersonProfileImageResponse
import com.giraffe.media.person.model.dto.PersonTvCastItemResponse
import com.giraffe.media.person.model.dto.SearchPersonResponse
import com.giraffe.media.person.util.RequestBuilder
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

    override suspend fun getPersonTvCredits(personId: Int): PersonTvCastItemResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = "$PERSON_DETAILS_END_POINT/$personId/$TV_CREDITS_END_POINT",
                params = mapOf(LANGUAGE to "en-US")
            )
    }

    override suspend fun getPersonDetails(personId: Int): PersonDetailsResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = "$PERSON_DETAILS_END_POINT/$personId",
                params = mapOf(LANGUAGE to "en-US")
            )
    }

    override suspend fun getPersonImages(personId: Int): PersonProfileImageResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = "$PERSON_DETAILS_END_POINT/$personId/$PERSON_IMAGES_END_POINT"
            )
    }

    override suspend fun getCreditsBySeriesId(seriesId: Int): CreditsResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = "$CREDITS_SHOW_END_POINT/$seriesId/$CREDITS",
                params = mapOf(LANGUAGE to "en-US")
            )
    }

    override suspend fun getPersonMovieCredits(personId: Int): PersonMovieCreditsResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = "$PERSON_DETAILS_END_POINT/$personId/$MOVIE_CREDITS_END_POINT",
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
        private const val PERSON_IMAGES_END_POINT = "images"
        private const val MOVIE_CREDITS_END_POINT = "movie_credits"
        private const val TV_CREDITS_END_POINT = "tv_credits"

        private const val CREDITS_MOVIE_END_POINT = "movie"
        private const val CREDITS = "credits"
        private const val CREDITS_SHOW_END_POINT = "tv"
        private const val SEARCH_PERSON_END_POINT = "search/person"
        private const val QUERY = "query"
        private const val INCLUDE_ADULT = "include_adult"
        private const val LANGUAGE = "language"
        private const val PAGE = "page"
        private const val PERSON_DETAILS_END_POINT = "person"

    }
}

