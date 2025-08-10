package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class AddMovieRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, rating: Float) =
        movieRepository.addRating(movieId = movieId, rating = rating)
}