package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import javax.inject.Inject

class RemoveSeriesFromCollectionUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionId: Int,
        seriesId: Int
    ) = collectionsRepository.removeSeriesFromCollection(
        collectionId = collectionId,
        seriesId = seriesId
    )
}