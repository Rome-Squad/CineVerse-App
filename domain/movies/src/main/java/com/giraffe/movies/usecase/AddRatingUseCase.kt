package com.giraffe.movies.usecase

import com.giraffe.movies.repository.MoviesRepository

class AddRatingUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, ratingValue: Float) {
        return repository.addRating(movieId, ratingValue)
    }
}