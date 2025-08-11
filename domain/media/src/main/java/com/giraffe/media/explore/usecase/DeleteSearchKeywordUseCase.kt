package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.SearchRepository
import javax.inject.Inject

class DeleteSearchKeywordUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(keyword: String) {
        repository.deleteSearchKeyword(keyword)
    }
}
