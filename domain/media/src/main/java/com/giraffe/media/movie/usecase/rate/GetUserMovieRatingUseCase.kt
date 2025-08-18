package com.giraffe.media.movie.usecase.rate

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetUserMovieRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) =
        movieRepository.getUserRatedById(movieId)

}