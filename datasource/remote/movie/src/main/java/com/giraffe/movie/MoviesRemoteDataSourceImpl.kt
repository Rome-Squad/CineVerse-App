package com.giraffe.movie

import com.giraffe.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.movie.datasource.remote.dto.AccountStatesDto
import com.giraffe.movie.datasource.remote.dto.GuestSessionDto
import com.giraffe.movie.datasource.remote.dto.MovieDetailsDto
import com.giraffe.movie.datasource.remote.dto.MovieDto
import com.giraffe.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.movie.datasource.remote.dto.MovieReviewDto
import com.giraffe.movie.datasource.remote.dto.RatingRequest
import com.giraffe.movie.datasource.remote.dto.ReviewsResponseDto
import com.giraffe.movie.response.GenreResponse
import com.giraffe.movie.response.MoviesListResponse
import com.giraffe.movie.utils.handleRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class MoviesRemoteDataSourceImpl(
    val client: HttpClient,
    val baseUrl: String,
    val accessToken: String,
) : MoviesRemoteDataSource {
    override suspend fun getMovieById(movieId: Int): MovieDetailsDto {
        return handleRequest<MovieDetailsDto> {
            client.get("$baseUrl$MOVIE_BY_ID_URL/$movieId") {
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }
    }

    override suspend fun getMoviesByName(movieName: String): List<MovieDto> {
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

    override suspend fun getMovieGenres(): List<MovieGenreDto> {
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

    override suspend fun getMovieReviews(movieId: Int): List<MovieReviewDto> {
        val response = handleRequest<ReviewsResponseDto> {
            client.get ("$baseUrl/movie/$movieId/reviews"){
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }
        return response.results
    }

    override suspend fun createGuestSession(): GuestSessionDto {
        return handleRequest {
            client.get("$baseUrl/authentication/guest_session/new") {
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }
    }

    override suspend fun addRating(
        movieId: Int,
        sessionId: String,
        request: RatingRequest
    ) {
        return handleRequest {
            client.post("$baseUrl/movie/$movieId/rating") {
                url {
                    parameters.append("session_id", sessionId)
                }
                contentType(ContentType.Application.Json)
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
                setBody(request)
            }
        }
    }

    override suspend fun getAccountStates(
        movieId: Int,
        sessionId: String
    ): AccountStatesDto {
        return handleRequest {
            client.get("$baseUrl/movie/$movieId/account_states") {
                url {
                    parameters.append("session_id", sessionId)
                }
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }
    }

    companion object {
        private const val MOVIE_BY_ID_URL = "movie"
        private const val MOVIES_BY_NAME_URL = "search/movie"
        private const val GENRES_URL = "genre/movie/list"
        private const val MOVIES_BY_GENRE_URL = "discover/movie"
    }
}