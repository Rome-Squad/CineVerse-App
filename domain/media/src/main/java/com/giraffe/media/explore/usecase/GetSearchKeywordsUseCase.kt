package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow

class GetSearchKeywordsUseCase(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke(query: String): Flow<List<SearchKeyword>> {
        return repository.getSearchKeywords(query.trim())
    }
}
