package com.giraffe.movie

import com.giraffe.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.movie.datasource.remote.dto.GenreDTO
import com.giraffe.movie.datasource.remote.dto.MovieDto
import com.giraffe.movie.response.GenreResponse
import com.giraffe.movie.response.MoviesListResponse
import com.giraffe.movie.utils.handleRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class MovieRemoteDataSource(
    val client: HttpClient,
    val baseUrl: String,
    val accessToken: String,
) : MoviesRemoteDataSource {
    override suspend fun getMovieById(movieId: Int): MovieDto {
        return handleRequest<MovieDto> {
            client.get(baseUrl + "$MOVIE_BY_ID_URL$movieId") {
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }
    }

    override suspend fun getMovieByName(movieName: String): List<MovieDto> {
        val response: MoviesListResponse = handleRequest<MoviesListResponse> {
            client.get(baseUrl + MOVIES_BY_NAME_URL) {
                url {
                    parameters.append("query", movieName)
                }
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }
        return response.results
    }

    override suspend fun getMovieGenres(): List<GenreDTO> {
        val response =
            handleRequest<GenreResponse> {
                client.get(baseUrl + GENRES_URL) {
                    headers {
                        append("Authorization", "Bearer $accessToken")
                    }
                }
            }
        return response.genres
    }

    override suspend fun getMoviesByGenre(genreId: Int): List<MovieDto> {
        val response =
            handleRequest<MoviesListResponse> {
                client.get(baseUrl + MOVIES_BY_GENRE_URL) {
                    url {
                        parameters.append("with_genres", genreId.toString())
                    }
                    headers {
                        append("Authorization", "Bearer $accessToken")
                    }
                }
            }
        return response.results
    }

    companion object {
        private const val MOVIE_BY_ID_URL = "movie"
        private const val MOVIES_BY_NAME_URL = "search/movie"
        private const val GENRES_URL = "genre/movie/list"
        private const val MOVIES_BY_GENRE_URL = "discover/movie"
    }
}