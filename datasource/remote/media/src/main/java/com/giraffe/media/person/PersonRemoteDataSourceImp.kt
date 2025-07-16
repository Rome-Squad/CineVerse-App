package com.giraffe.media.person

import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.model.dto.CreditsDto
import com.giraffe.media.person.model.dto.PersonDetailsDto
import com.giraffe.media.person.model.dto.PersonProfileImageDto
import com.giraffe.media.person.response.PersonMovieCreditsResponse
import com.giraffe.media.person.response.PersonTvCastItemResponse
import com.giraffe.media.person.response.SearchPersonResponse
import com.giraffe.media.util.RequestBuilder

class PersonRemoteDataSourceImp(
    private val requestBuilder: RequestBuilder
) : PersonRemoteDataSource {
    override suspend fun searchByName(personName: String) =
        requestBuilder.get<SearchPersonResponse>(
            endpoint = SEARCH_PERSON_END_POINT,
            params = mapOf(
                QUERY to personName,
                INCLUDE_ADULT to "false",
                LANGUAGE to "en-US",
                PAGE to "1"
            )
        ).people

    override suspend fun getPersonTvCredits(personId: Int) =
        requestBuilder.get<PersonTvCastItemResponse>(
            endpoint = "$PERSON_DETAILS_END_POINT/$personId/$TV_CREDITS_END_POINT",
            params = mapOf(LANGUAGE to "en-US")
        ).cast

    override suspend fun getPersonDetails(personId: Int) =
        requestBuilder.get<PersonDetailsDto>(
            endpoint = "$PERSON_DETAILS_END_POINT/$personId",
            params = mapOf(LANGUAGE to "en-US")
        )

    override suspend fun getPersonImages(personId: Int) =
        requestBuilder.get<PersonProfileImageDto>(
            endpoint = "$PERSON_DETAILS_END_POINT/$personId/$PERSON_IMAGES_END_POINT"
        )

    override suspend fun getCreditsBySeriesId(seriesId: Int) =
        requestBuilder.get<CreditsDto>(
            endpoint = "$CREDITS_SHOW_END_POINT/$seriesId/$CREDITS",
            params = mapOf(LANGUAGE to "en-US")
        )

    override suspend fun getPersonMovieCredits(personId: Int) =
        requestBuilder.get<PersonMovieCreditsResponse>(
            endpoint = "$PERSON_DETAILS_END_POINT/$personId/$MOVIE_CREDITS_END_POINT",
            params = mapOf(LANGUAGE to "en-US")
        ).cast

    override suspend fun getCreditsByMovieId(movieId: Int) =
        requestBuilder.get<CreditsDto>(
            endpoint = "$CREDITS_MOVIE_END_POINT/$movieId/$CREDITS",
            params = mapOf(LANGUAGE to "en-US")
        )

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