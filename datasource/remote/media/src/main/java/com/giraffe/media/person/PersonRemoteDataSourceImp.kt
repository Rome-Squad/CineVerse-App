package com.giraffe.media.person

import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.person.response.PersonCreditsResponse
import com.giraffe.media.person.response.SearchPersonResponse
import com.giraffe.media.util.RequestBuilder

class PersonRemoteDataSourceImp(
    private val requestBuilder: RequestBuilder
) : PersonRemoteDataSource {
    private val defaultLanguageParams = mapOf(LANGUAGE to "en-US")

    override suspend fun searchByName(personName: String, page: Int) =
        requestBuilder.get<SearchPersonResponse>(
            endpoint = SEARCH_PERSON_END_POINT,
            params = mapOf(
                QUERY to personName,
                INCLUDE_ADULT to "false",
                LANGUAGE to "en-US",
                PAGE to page.toString()
            )
        ).people

    override suspend fun getPersonMediaCredits(personId: Int) =
        requestBuilder.get<PersonCreditsResponse>(
            endpoint = "$PERSON_END_POINT/$personId/$COMBINED_CREDITS_END_POINT",
            params = defaultLanguageParams
        ).cast


    override suspend fun getPersonSocialMedia(personId: Int) =
        requestBuilder.get<PersonSocialMediaDto>(
            endpoint = "/$PERSON_END_POINT/$personId/$EXTERNAL_IDS_END_POINT",
            params = defaultLanguageParams
        )

    override suspend fun getPersonDetails(personId: Int) =
        requestBuilder.get<PersonDetailsDto>(
            endpoint = "$PERSON_END_POINT/$personId",
            params = defaultLanguageParams
        )

    override suspend fun getPersonImages(personId: Int) =
        requestBuilder.get<PersonProfileImageDto>(
            endpoint = "$PERSON_END_POINT/$personId/$PERSON_IMAGES_END_POINT",
        )

    override suspend fun getCreditsBySeriesId(seriesId: Int) =
        requestBuilder.get<CreditsDto>(
            endpoint = "$CREDITS_SHOW_END_POINT/$seriesId/$CREDITS",
            params = defaultLanguageParams
        )

    override suspend fun getCreditsByMovieId(movieId: Int) =
        requestBuilder.get<CreditsDto>(
            endpoint = "$CREDITS_MOVIE_END_POINT/$movieId/$CREDITS",
            params = defaultLanguageParams
        )

    companion object {
        private const val EXTERNAL_IDS_END_POINT = "external_ids"
        private const val PERSON_IMAGES_END_POINT = "images"
        private const val COMBINED_CREDITS_END_POINT = "combined_credits"
        private const val CREDITS_MOVIE_END_POINT = "movie"
        private const val CREDITS = "credits"
        private const val CREDITS_SHOW_END_POINT = "tv"
        private const val SEARCH_PERSON_END_POINT = "search/person"
        private const val QUERY = "query"
        private const val INCLUDE_ADULT = "include_adult"
        private const val LANGUAGE = "language"
        private const val PAGE = "page"
        private const val PERSON_END_POINT = "person"

    }
}