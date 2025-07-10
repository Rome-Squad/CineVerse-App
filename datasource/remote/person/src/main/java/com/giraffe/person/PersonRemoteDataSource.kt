package com.giraffe.person

import com.giraffe.person.response.SearchPersonResponse

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): SearchPersonResponse
}