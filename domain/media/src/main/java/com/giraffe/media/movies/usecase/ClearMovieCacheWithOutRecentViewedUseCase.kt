package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class ClearMovieCacheWithOutRecentViewedUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke() {
        repository.clearMovieCacheWithOutRecentViewed()
    }
}