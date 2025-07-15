package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository

class ClearCacheUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke() {
        repository.clearCache()
    }
}