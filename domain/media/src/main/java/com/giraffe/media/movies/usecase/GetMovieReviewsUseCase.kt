package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Review
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, pageNumber: Int): List<Review> {
        return repository.getMovieReviews(movieId, pageNumber)
    }
}