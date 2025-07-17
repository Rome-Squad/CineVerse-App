package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository

class SearchMovieByNameUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieName: String): List<Movie> {
        val searchResults = repository.searchMovieByName(movieName)

        val sortedPreferences = repository.getMoviesGenres()

        if (sortedPreferences.isEmpty() || sortedPreferences.first().rank == 0) {
            return searchResults
        }

        val favoriteGenreId = sortedPreferences.first().id

        return searchResults.sortedByDescending { movie ->
            movie.genresID.contains(favoriteGenreId)
        }
    }
}