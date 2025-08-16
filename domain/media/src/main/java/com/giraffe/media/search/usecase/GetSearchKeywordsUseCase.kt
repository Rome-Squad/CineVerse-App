package com.giraffe.media.search.usecase

import com.giraffe.media.search.entity.SearchKeyword
import com.giraffe.media.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchKeywordsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): Flow<List<SearchKeyword>> {
        return repository.getSearchKeywords(query.trim())
    }
}
