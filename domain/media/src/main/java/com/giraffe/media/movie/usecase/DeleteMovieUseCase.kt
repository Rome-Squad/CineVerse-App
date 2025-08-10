package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import jakarta.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) =
        movieRepository.deleteById(movieId)
}