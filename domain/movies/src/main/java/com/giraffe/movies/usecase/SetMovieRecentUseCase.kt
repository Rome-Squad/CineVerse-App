package com.giraffe.movies.usecase

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository

class SetMovieRecentUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie, isRecent: Boolean) {
        repository.setMovieRecent(
            movie = movie,
            isRecent = isRecent
        )
    }
}