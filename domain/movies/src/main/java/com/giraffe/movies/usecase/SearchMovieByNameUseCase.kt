package com.giraffe.movies.usecase

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository
class SearchMovieByNameUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieName: String): List<Movie> {
        return repository.searchMovieByName(movieName)
    }
}