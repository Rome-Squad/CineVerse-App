package com.giraffe.person.remote

import com.giraffe.person.remote.response.SearchPersonResponse
import com.giraffe.person.remote.response.CreditsMovieResponse

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): SearchPersonResponse

    suspend fun getCreditsByMovieId(movieId: Int): CreditsMovieResponse
}