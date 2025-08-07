package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class ClearMoviesCacheUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun clearMovieCache() {
        repository.clearMovieCache()
        repository.clearMovieGenres()
    }

    suspend fun clearMovieCacheWithOutRecentViewed() {
        repository.clearMovieCacheWithOutRecentViewed()
        repository.clearMovieGenres()
    }

    suspend fun clearRecentlyViewedMovies() {
        repository.clearRecentlyViewedMovies()
    }
}