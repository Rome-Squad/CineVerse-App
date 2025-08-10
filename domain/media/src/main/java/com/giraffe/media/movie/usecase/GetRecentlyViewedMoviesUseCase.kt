package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetRecentlyViewedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() =
        movieRepository.getRecentlyViewed()
}