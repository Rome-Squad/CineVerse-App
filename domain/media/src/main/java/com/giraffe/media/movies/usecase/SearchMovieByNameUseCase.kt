package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository

class SearchMovieByNameUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieName: String): List<Movie> {
        val searchResults = repository.searchMovieByName(movieName)

        val userPreferences = repository.getUserGenrePreferences()

        if (userPreferences.isEmpty()) {
            return searchResults
        }

        val favoriteGenreId = userPreferences.first().genreId

        return searchResults.sortedByDescending { movie ->
            movie.genresID.contains(favoriteGenreId)
        }
    }
}