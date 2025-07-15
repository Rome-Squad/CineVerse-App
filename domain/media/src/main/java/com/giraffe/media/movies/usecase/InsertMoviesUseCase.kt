package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository

class InsertMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movies: List<Movie>) {
        repository.insertMovies(movies)
    }
}