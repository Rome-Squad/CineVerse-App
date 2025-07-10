package com.giraffe.explore.usecase

import com.giraffe.explore.repository.ExploreRepository

class ClearSearchHistoryUseCase(
    private val repository: ExploreRepository
) {
    suspend fun execute() {
        repository.clearSearchHistory()
    }
}
