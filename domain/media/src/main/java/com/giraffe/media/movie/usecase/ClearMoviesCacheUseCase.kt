package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class ClearMoviesCacheUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun clearMovieCache() {
        repository.clearAll()
        repository.clearGenres()
    }

    suspend fun clearMovieCacheWithOutRecentViewed() {
        repository.clearExceptRecentlyViewed()
        repository.clearGenres()
    }

    suspend fun clearRecentlyViewedMovies() {
        repository.clearRecentlyViewed()
    }
}