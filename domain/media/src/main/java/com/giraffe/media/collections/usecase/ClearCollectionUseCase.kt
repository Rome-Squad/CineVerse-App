package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import javax.inject.Inject

class ClearCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionId: Int
    ) = collectionsRepository.clearCollection(
        collectionId
    )
}