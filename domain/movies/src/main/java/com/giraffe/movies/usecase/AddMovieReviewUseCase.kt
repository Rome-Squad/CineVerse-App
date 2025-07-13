package com.giraffe.movies.usecase

import com.giraffe.movies.entity.MovieReview
import com.giraffe.movies.repository.MoviesRepository

class AddMovieReviewUseCase(
    private val repository : MoviesRepository
) {
    suspend operator fun invoke(review : MovieReview) {
        repository.addMovieReview(review)
    }
}