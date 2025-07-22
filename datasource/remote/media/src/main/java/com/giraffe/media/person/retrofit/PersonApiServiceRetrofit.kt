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

    @GET("search/person")
    suspend fun searchByName(
        @Query("query") name: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<SearchPersonResponse>

    @GET("person/{id}/combined_credits")
    suspend fun getPersonMediaCredits(
        @Path("id") personId: Int,
        @Query("language") language: String = "en-US"
    ): Response<PersonCreditsResponse>

    @GET("person/{id}/external_ids")
    suspend fun getPersonSocialMedia(
        @Path("id") personId: Int,
        @Query("language") language: String = "en-US"
    ): Response<PersonSocialMediaDto>


    @GET("person/{id}")
    suspend fun getPersonDetails(
        @Path("id") personId: Int,
        @Query("language") language: String = "en-US"
    ): Response<PersonDetailsDto>

    @GET("person/{id}/images")
    suspend fun getPersonImages(
        @Path("id") personId: Int
    ): Response<PersonProfileImageDto>


    @GET("tv/{seriesId}/credits")
    suspend fun getCreditsBySeriesId(
        @Path("seriesId") seriesId: Int,
        @Query("language") language: String = "en-US"
    ): Response<CreditsDto>


    @GET("movie/{movieId}/credits")
    suspend fun getCreditsByMovieId(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "en-US"
    ): Response<CreditsDto>

}
