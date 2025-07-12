package com.giraffe.explore.usecase

import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow

class GetSearchKeywordsUseCase(
    private val repository: ExploreRepository
) {
    suspend fun execute(query: String): Flow<List<SearchKeyword>> {
        return repository.getSearchKeywords(query.trim())
    }
}
