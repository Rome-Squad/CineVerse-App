package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class SetMovieRecentUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie, isRecent: Long = System.currentTimeMillis()) {
        repository.setMovieRecent(
            movie = movie,
            isRecentViewed = isRecent
        )
    }
}