package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.ExploreRepository
import javax.inject.Inject

class InsertSearchKeywordUseCase @Inject constructor(
    private val repository: ExploreRepository
) {
    suspend operator fun invoke(searchKeyword: String) {
        repository.insertSearchKeyword(searchKeyword)
    }
}
