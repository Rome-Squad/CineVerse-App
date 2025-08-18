package com.giraffe.media.movie.usecase.recentlyViewed

import com.giraffe.media.movie.repository.MovieRepository
import jakarta.inject.Inject

class DeleteRecentlyViewedMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) =
        movieRepository.deleteRecentlyViewedMovieById(movieId)
}