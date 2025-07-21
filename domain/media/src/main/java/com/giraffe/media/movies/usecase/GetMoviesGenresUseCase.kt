package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.repository.MoviesRepository

class GetMoviesGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return repository.getMoviesGenres()
    }
}