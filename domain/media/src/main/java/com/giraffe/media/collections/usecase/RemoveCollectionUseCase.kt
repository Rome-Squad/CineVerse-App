package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository

class RemoveCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionId: Int
    ) = collectionsRepository.removeCollection(
        collectionId
    )
}