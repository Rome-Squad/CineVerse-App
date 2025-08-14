package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class ClearMoviesCacheUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun clearAll() {
        movieRepository.clearAll()
    }

    suspend fun clearExceptRecentlyViewed() {
        movieRepository.clearExceptRecentlyViewed()
    }

    suspend fun clearRecentlyViewed() =
        movieRepository.clearRecentlyViewed()
}