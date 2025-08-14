package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetRecentlyViewedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(page: Int = 1, pageSize: Int = 10) =
        movieRepository.getRecentlyViewed(page, pageSize)
}