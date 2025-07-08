package com.giraffe.person

import com.giraffe.person.response.SearchPersonResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class RemoteDataSourceImp(
    private val client: HttpClient
) : RemoteDataSource {
    override suspend fun searchByName(personName: String): SearchPersonResponse {
        return safeCall<SearchPersonResponse> {
            client.get(BASE_URL) {
                url {
                    parameters.append("query", personName)
                    parameters.append("include_adult", "false")
                    parameters.append("language", "en-US")
                    parameters.append("page", "1")
                }
                headers {
                    append("Authorization", "Bearer $API_KEY")
                }
            }
        }
    }

    suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): T {
        val response = try {
            execute()
        } catch (exception: Exception) {
            coroutineContext.ensureActive()
            throw exception
        }
        return response.body<T>()
    }

    companion object {
        private const val API_KEY =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNDdkOTgxNjUxZWNmNTMxYmI0ZGRkNTMyYTE5ZDhhZSIsIm5iZiI6MTY4ODU0NjY1OC40NTUwMDAyLCJzdWIiOiI2NGE1MmQ2MjE1OGM4NTAwZmZiYTU1YWMiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.vJCN3YBp6HcS1d2zHyoaDMtXpaxu6-kaubtn_G4Z8bQ"
        private const val BASE_URL = "https://api.themoviedb.org/3/search/person"
    }
}

