package com.giraffe.person.remote

import com.giraffe.person.remote.response.SearchPersonResponse
import com.giraffe.person.remote.response.CreditsResponse

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): SearchPersonResponse
    suspend fun getCreditsByShowId(showId: Int): CreditsResponse
    suspend fun getCreditsByMovieId(movieId: Int): CreditsResponse
}