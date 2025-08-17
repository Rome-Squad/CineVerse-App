package com.giraffe.media.search.usecase

import com.giraffe.media.search.repository.SearchRepository
import javax.inject.Inject

class DeleteSearchKeywordUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(keyword: String) {
        repository.deleteSearchKeyword(keyword)
    }
}
