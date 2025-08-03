package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository

class AddSeriesToCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionId: Int,
        seriesId: Int
    ) = collectionsRepository.addSeriesToCollection(
        collectionId = collectionId,
        seriesId = seriesId
    )
}