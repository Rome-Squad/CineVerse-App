package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.ExploreRepository

class InsertSearchKeywordUseCase(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke(searchKeyword: String) {
        repository.insertSearchKeyword(searchKeyword)
    }
}
