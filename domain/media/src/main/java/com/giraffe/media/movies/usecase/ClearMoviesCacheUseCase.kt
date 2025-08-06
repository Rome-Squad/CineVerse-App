package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class ClearMoviesCacheUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(
        clearOnlyRecentlyViewed: Boolean = false,
        excludeRecentlyViewed: Boolean = false
    ) =
        repository.clearMovieCache(
            clearOnlyRecentlyViewed = clearOnlyRecentlyViewed,
            excludeRecentlyViewed = excludeRecentlyViewed
        )

}