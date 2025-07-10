package com.giraffe.person.remote

import com.giraffe.person.remote.response.SearchPersonResponse

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): SearchPersonResponse
}