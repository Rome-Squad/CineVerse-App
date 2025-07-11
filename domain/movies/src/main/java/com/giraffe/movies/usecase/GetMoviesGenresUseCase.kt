package com.giraffe.movies.usecase

import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository

class GetMoviesGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): List<MovieGenre> {
        return repository.getMoviesGenres()
    }
}