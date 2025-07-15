package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.ExploreRepository

class ClearSearchHistoryUseCase(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke() {
        repository.clearSearchHistory()
    }
}
