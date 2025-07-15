package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository

class AddMovieRatingUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, ratingValue: Float) {
        return repository.addRating(movieId, ratingValue)
    }
}