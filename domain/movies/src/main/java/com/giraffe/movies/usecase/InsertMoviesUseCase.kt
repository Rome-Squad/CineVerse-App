package com.giraffe.movies.usecase

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository

class InsertMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movies: List<Movie>) {
        repository.insertMovies(movies)
    }
}