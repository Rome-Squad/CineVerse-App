package com.giraffe.media.person.retrofit

import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.person.response.PersonCreditsResponse
import com.giraffe.media.person.response.SearchPersonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApiServiceRetrofit {

    @GET(ENDPOINT_SEARCH_PERSON)
    suspend fun searchByName(
        @Query(PARAM_QUERY) name: String,
        @Query(PARAM_INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(PARAM_LANGUAGE) language: String = "en-US",
        @Query(PARAM_PAGE) page: Int = 1
    ): Response<SearchPersonResponse>

    @GET(ENDPOINT_PERSON_COMBINED_CREDITS)
    suspend fun getPersonMediaCredits(
        @Path(PATH_ID) personId: Int,
        @Query(PARAM_LANGUAGE) language: String = "en-US"
    ): Response<PersonCreditsResponse>

    @GET(ENDPOINT_PERSON_EXTERNAL_IDS)
    suspend fun getPersonSocialMedia(
        @Path(PATH_ID) personId: Int,
        @Query(PARAM_LANGUAGE) language: String = "en-US"
    ): Response<PersonSocialMediaDto>


    @GET(ENDPOINT_PERSON_DETAILS)
    suspend fun getPersonDetails(
        @Path(PATH_ID) personId: Int,
        @Query(PARAM_LANGUAGE) language: String = "en-US"
    ): Response<PersonDetailsDto>

    @GET(ENDPOINT_PERSON_IMAGES)
    suspend fun getPersonImages(
        @Path(PATH_ID) personId: Int
    ): Response<PersonProfileImageDto>


    @GET(ENDPOINT_TV_CREDITS)
    suspend fun getCreditsBySeriesId(
        @Path(PATH_SERIES_ID) seriesId: Int,
        @Query(PARAM_LANGUAGE) language: String = "en-US"
    ): Response<CreditsDto>


    @GET(ENDPOINT_MOVIE_CREDITS)
    suspend fun getCreditsByMovieId(
        @Path(PATH_MOVIE_ID) movieId: Int,
        @Query(PARAM_LANGUAGE) language: String = "en-US"
    ): Response<CreditsDto>

    companion object {
        // Endpoints
        private const val ENDPOINT_SEARCH_PERSON = "search/person"
        private const val ENDPOINT_PERSON_COMBINED_CREDITS = "person/{id}/combined_credits"
        private const val ENDPOINT_PERSON_EXTERNAL_IDS = "person/{id}/external_ids"
        private const val ENDPOINT_PERSON_DETAILS = "person/{id}"
        private const val ENDPOINT_PERSON_IMAGES = "person/{id}/images"
        private const val ENDPOINT_TV_CREDITS = "tv/{seriesId}/credits"
        private const val ENDPOINT_MOVIE_CREDITS = "movie/{movieId}/credits"

        // Path Parameters
        private const val PATH_ID = "id"
        private const val PATH_SERIES_ID = "seriesId"
        private const val PATH_MOVIE_ID = "movieId"

        // Query Parameters
        private const val PARAM_QUERY = "query"
        private const val PARAM_INCLUDE_ADULT = "include_adult"
        private const val PARAM_LANGUAGE = "language"
        private const val PARAM_PAGE = "page"
    }
}