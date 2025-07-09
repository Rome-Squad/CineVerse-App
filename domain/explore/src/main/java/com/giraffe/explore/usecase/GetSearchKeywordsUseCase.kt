package com.giraffe.explore.usecase

import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.repository.ExploreRepository

class GetSearchKeywordsUseCase(
    private val repository: ExploreRepository
) {
    suspend fun execute(query: String): List<SearchKeyword> {
        return repository.getSearchKeywords(query)
    }
}
