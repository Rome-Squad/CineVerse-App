package com.giraffe.media.person.remote

import com.giraffe.person.remote.response.SearchPersonResponse
import com.giraffe.person.remote.response.CreditsResponse
import com.giraffe.person.remote.response.PersonDetailsResponse
import com.giraffe.person.remote.response.PersonMovieCastItemResponse
import com.giraffe.person.remote.response.PersonMovieCreditsResponse
import com.giraffe.person.remote.response.PersonProfileImageResponse
import com.giraffe.person.remote.response.PersonTvCastItemResponse

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): SearchPersonResponse
    suspend fun getCreditsBySeriesId(seriesId: Int): CreditsResponse
    suspend fun getCreditsByMovieId(movieId: Int): CreditsResponse
    suspend fun getPersonDetails(personId: Int): PersonDetailsResponse
    suspend fun getPersonImages(personId: Int): PersonProfileImageResponse
    suspend fun getPersonMovieCredits(personId: Int): PersonMovieCreditsResponse

    suspend fun getPersonTvCredits(personId: Int): PersonTvCastItemResponse
}