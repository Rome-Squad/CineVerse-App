package com.giraffe.movies.usecase

import com.giraffe.movies.repository.MoviesRepository

class ClearCacheUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke() {
        repository.clearCache()
    }
}