package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository

class GetCollectionsUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke() = collectionsRepository.getCollections()
}