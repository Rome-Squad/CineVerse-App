package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository

class GetUserMovieRatingUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): Float {
        return moviesRepository.getUserMovieRating(movieId)
    }
}