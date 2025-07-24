package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository

class GetUpcomingMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(): List<Movie> = repository.getUpcomingMovies()
}