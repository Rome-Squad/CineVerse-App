package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
class SearchMovieByNameUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieName: String): List<Movie> {
        return repository.searchMovieByName(movieName)
    }
}