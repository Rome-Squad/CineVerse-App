package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import jakarta.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int) {
        // Clear the movie from the local database
        moviesRepository.deleteMovieById(movieId)
    }
}