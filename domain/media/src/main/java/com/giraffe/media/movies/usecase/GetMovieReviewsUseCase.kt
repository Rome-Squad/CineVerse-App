package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.MovieReview
import com.giraffe.media.movies.repository.MoviesRepository

class GetMovieReviewsUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, pageNumber: Int, pageSize: Int): List<MovieReview> {
        return repository.getMovieReviews(movieId)
    }
}