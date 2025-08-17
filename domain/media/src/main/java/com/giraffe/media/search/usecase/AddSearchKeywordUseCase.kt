package com.giraffe.media.search.usecase

import com.giraffe.media.search.repository.SearchRepository
import javax.inject.Inject

class AddSearchKeywordUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchKeyword: String) {
        repository.addSearchKeyword(searchKeyword)
    }
}
