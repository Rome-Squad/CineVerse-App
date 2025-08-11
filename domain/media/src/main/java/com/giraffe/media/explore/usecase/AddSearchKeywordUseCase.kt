package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.SearchRepository
import javax.inject.Inject

class AddSearchKeywordUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchKeyword: String) {
        repository.addSearchKeyword(searchKeyword)
    }
}
