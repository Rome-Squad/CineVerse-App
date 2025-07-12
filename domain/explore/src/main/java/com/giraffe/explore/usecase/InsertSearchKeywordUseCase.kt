package com.giraffe.explore.usecase

import com.giraffe.explore.repository.ExploreRepository

class InsertSearchKeywordUseCase(
    private val repository: ExploreRepository
) {
    suspend fun execute(searchKeyword: String) {
        repository.insertSearchKeyword(searchKeyword)
    }
}
