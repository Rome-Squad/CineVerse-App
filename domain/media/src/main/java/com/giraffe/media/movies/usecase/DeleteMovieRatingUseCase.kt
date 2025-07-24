package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository

class DeleteMovieRatingUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int) {
        moviesRepository.deleteMovieRating(movieId)
    }
}