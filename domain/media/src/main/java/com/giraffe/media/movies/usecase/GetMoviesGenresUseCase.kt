package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.movies.repository.MoviesRepository

class GetMoviesGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): List<MovieGenre> {
        return repository.getMoviesGenres()
    }
}