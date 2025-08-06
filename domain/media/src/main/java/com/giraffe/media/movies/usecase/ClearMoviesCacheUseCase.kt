package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class ClearMoviesCacheUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun clearMovieCache() {
        repository.clearMovieCache()
    }

    suspend fun clearMovieCacheWithOutRecentViewed() {
        repository.clearMovieCacheWithOutRecentViewed()

    }

    suspend fun clearRecentlyViewedMovies() {
        repository.clearRecentlyViewedMovies()
    }
}