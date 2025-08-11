package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchKeywordsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): Flow<List<SearchKeyword>> {
        return repository.getSearchKeywords(query.trim())
    }
}
