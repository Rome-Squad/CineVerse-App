package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class RefreshRecentlyViewedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() =
        movieRepository.refreshRecentlyViewedMovies()
}