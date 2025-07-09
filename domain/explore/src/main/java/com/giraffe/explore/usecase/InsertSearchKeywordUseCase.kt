package com.giraffe.explore.usecase

import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.repository.ExploreRepository

class InsertSearchKeywordUseCase(
    private val repository: ExploreRepository
) {
    suspend fun execute(searchKeyword: SearchKeyword) {
        repository.insertSearchKeyword(searchKeyword)
    }
}
