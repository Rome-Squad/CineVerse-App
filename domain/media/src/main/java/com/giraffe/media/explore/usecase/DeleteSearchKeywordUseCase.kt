package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.repository.ExploreRepository

class DeleteSearchKeywordUseCase(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke(searchKeyword: SearchKeyword) {
        repository.deleteSearchKeyword(searchKeyword)
    }
}
