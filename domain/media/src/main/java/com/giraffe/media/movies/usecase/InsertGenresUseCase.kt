package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.movies.repository.MoviesRepository

class InsertGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genres: List<MovieGenre>) {
        repository.insertGenres(genres)
    }
}