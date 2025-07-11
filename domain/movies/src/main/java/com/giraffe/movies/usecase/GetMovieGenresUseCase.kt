package com.giraffe.movies.usecase

import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository

class GetMovieGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): List<MovieGenre> {
        return repository.getMovieGenres()
    }
}