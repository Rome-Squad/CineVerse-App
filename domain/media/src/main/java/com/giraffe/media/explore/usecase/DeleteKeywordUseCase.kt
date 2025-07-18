package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.ExploreRepository

class DeleteKeywordUseCase(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke(keyword: String) {
        repository.deleteKeyword(keyword)
    }
}
