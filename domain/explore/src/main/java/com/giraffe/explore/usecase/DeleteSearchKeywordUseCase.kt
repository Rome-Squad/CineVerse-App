package com.giraffe.explore.usecase

import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.repository.ExploreRepository

class DeleteSearchKeywordUseCase(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke(searchKeyword: SearchKeyword) {
        repository.deleteSearchKeyword(searchKeyword)
    }
}
