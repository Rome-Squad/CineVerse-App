package com.giraffe.movie

import com.giraffe.movie.utils.handleRequest
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers

class MovieRemoteDataSource : MoviesRepository {

    val client: HttpClient = HttpClient(CIO)
    override suspend fun searchMovieByName(movieName: String): List<Movie> {
        val httpResponse = client.get(MOVIES_BY_NAME_URL) {
            url {
                parameters.append("query", movieName)
            }
            headers {
                append("Authorization", "Bearer $API_KEY")
            }
        }
        val movieResponse: MovieResponse = handleRequest<MovieResponse>(httpResponse)
        return movieResponse.results.map { it.toMovie() }
    }

    override suspend fun getMovieGenres(): List<MovieGenre> {
        val response = client.get(GENRES_URL) {
            headers {
                append("Authorization", "Bearer $API_KEY")
            }
        }
        val genreResponse: GenreResponse = handleRequest(response)
        return genreResponse.genres.map { it.toMovieGenre() }
    }

    override suspend fun getMoviesByGenre(genreId: Int): List<Movie> {
        val httpResponse = client.get(MOVIES_BY_GENRE_URL) {
            url {
                parameters.append("with_genres", genreId.toString())
            }
            headers {
                append("Authorization", "Bearer $API_KEY")
            }
        }
        val movieResponse: MovieResponse = handleRequest<MovieResponse>(httpResponse)
        return movieResponse.results.map { it.toMovie() }
    }

    companion object {
        private const val API_KEY =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmYjA4OTZlNmZlMmIxMGI1YTViYzE1ZDBkODM0YWU1YSIsIm5iZiI6MTc1MTk5MzA1NC43MDQsInN1YiI6IjY4NmQ0YWRlYTFhYWI0OTRiYzcwMzQzMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pvTPNoLWrO9AAD2MU0PqtJb7hgeyIooBthUlM4XqR38"
        private const val MOVIES_BY_NAME_URL = "https://api.themoviedb.org/3/search/movie"
        private const val GENRES_URL = "https://api.themoviedb.org/3/genre/movie/list"
        private const val MOVIES_BY_GENRE_URL = "https://api.themoviedb.org/3/discover/movie"
    }
}