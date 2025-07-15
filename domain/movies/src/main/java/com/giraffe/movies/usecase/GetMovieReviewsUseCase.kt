package com.giraffe.movies.usecase

import com.giraffe.movies.entity.MovieReview
import com.giraffe.movies.repository.MoviesRepository

class GetMovieReviewsUseCase(
    private val repository : MoviesRepository
) {
    suspend operator fun invoke(movieId : Int, pageNumber : Int, pageSize : Int) : List<MovieReview> {
        return repository.getMovieReviews(movieId)
    }
}