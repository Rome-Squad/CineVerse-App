package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class AddMovieRatingUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, ratingValue: Float) {
        return repository.addRating(movieId, ratingValue)
    }
}