package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.ExploreRepository
import javax.inject.Inject

class DeleteKeywordUseCase @Inject constructor(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke(keyword: String) {
        repository.deleteKeyword(keyword)
    }
}
