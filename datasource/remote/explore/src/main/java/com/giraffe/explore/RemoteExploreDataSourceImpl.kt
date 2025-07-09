package com.giraffe.explore

import com.giraffe.explore.datasource.remote.RemoteExploreDataSource
import com.giraffe.explore.model.SearchKeywordDto
import com.giraffe.explore.model.SearchKeywordResponse
import com.giraffe.explore.utils.safeNetworkRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter

class RemoteExploreDataSourceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val accessToken: String
) : RemoteExploreDataSource {
    override suspend fun getSearchKeywords(
        query: String,
        page: Int = 1
    ): List<SearchKeywordDto> {
        val response = safeNetworkRequest<SearchKeywordResponse> {
            httpClient.get("${baseUrl}search/keyword") {
                parameter("query", query)
                parameter("page", 1)
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }

        return response.results
    }
}