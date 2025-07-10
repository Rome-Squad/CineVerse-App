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
    val client: HttpClient
) : MoviesRemoteDataSource {
    override suspend fun getMovieById(movieId: Int): MovieDto {
        return handleRequest<MovieDto> {
            client.get("$MOVIE_BY_ID_URL$movieId") {
                headers {
                    append("Authorization", "Bearer $ACCESS_TOKEN")
                }
            }
        }
    }

    override suspend fun getMovieByName(movieName: String): List<MovieDto> {
        val response: MoviesListResponse = handleRequest<MoviesListResponse> {
            client.get(MOVIES_BY_NAME_URL) {
                url {
                    parameters.append("query", movieName)
                }
                headers {
                    append("Authorization", "Bearer $ACCESS_TOKEN")
                }
            }
        }
        return response.results
    }

    override suspend fun getMovieGenres(): List<GenreDTO> {
        val response =
            handleRequest<GenreResponse> {
            client.get(GENRES_URL) {
                headers {
                    append("Authorization", "Bearer $ACCESS_TOKEN")
                }
            }
        }
        return response.genres
    }

    override suspend fun getMoviesByGenre(genreId: Int): List<MovieDto> {
        val response =
            handleRequest<MoviesListResponse> {
            client.get(MOVIES_BY_GENRE_URL) {
                url {
                    parameters.append("with_genres", genreId.toString())
                }
                headers {
                    append("Authorization", "Bearer $ACCESS_TOKEN")
                }
            }
        }
        return response.results
    }

    companion object {
        private const val ACCESS_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmYjA4OTZlNmZlMmIxMGI1YTViYzE1ZDBkODM0YWU1YSIsIm5iZiI6MTc1MTk5MzA1NC43MDQsInN1YiI6IjY4NmQ0YWRlYTFhYWI0OTRiYzcwMzQzMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pvTPNoLWrO9AAD2MU0PqtJb7hgeyIooBthUlM4XqR38"
        private const val MOVIE_BY_ID_URL = "https://api.themoviedb.org/3/movie"
        private const val MOVIES_BY_NAME_URL = "https://api.themoviedb.org/3/search/movie"
        private const val GENRES_URL = "https://api.themoviedb.org/3/genre/movie/list"
        private const val MOVIES_BY_GENRE_URL = "https://api.themoviedb.org/3/discover/movie"
    }
}