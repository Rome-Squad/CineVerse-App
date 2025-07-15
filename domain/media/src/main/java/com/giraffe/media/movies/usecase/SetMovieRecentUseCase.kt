package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository

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