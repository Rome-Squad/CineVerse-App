package com.giraffe.person

import com.giraffe.person.response.SearchPersonResponse

interface RemoteDataSource {
    suspend fun searchByName(personName: String): SearchPersonResponse
}