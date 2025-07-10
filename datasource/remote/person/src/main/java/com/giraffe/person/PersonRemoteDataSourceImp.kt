package com.giraffe.person

import com.giraffe.person.response.SearchPersonResponse
import com.giraffe.person.util.RequestBuilder
import io.ktor.client.HttpClient

class PersonRemoteDataSourceImp(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val accessToken: String,
) : PersonRemoteDataSource {
    override suspend fun searchByName(personName: String): SearchPersonResponse {
        return RequestBuilder(httpClient, baseUrl, accessToken)
            .get(
                endpoint = SEARCH_PERSON_END_POINT,
                params = mapOf(
                    QUERY to personName,
                    INCLUDE_ADULT to "false",
                    LANGUAGE to "en-US",
                    PAGE to "1"
                )
            )
    }

    companion object {
        private const val SEARCH_PERSON_END_POINT = "search/person"
        private const val QUERY = "query"
        private const val INCLUDE_ADULT = "include_adult"
        private const val LANGUAGE = "language"
        private const val PAGE = "page"
    }
}

