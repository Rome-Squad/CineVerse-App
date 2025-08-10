package com.giraffe.media.movie.usecase

import com.giraffe.media.entity.Review
import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, pageNumber: Int): List<Review> {
        return repository.getMovieReviews(movieId, pageNumber)
    }
}