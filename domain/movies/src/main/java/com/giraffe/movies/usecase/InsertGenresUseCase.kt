package com.giraffe.movies.usecase

import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository

class InsertGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genres: List<MovieGenre>) {
        repository.insertGenres(genres)
    }
}