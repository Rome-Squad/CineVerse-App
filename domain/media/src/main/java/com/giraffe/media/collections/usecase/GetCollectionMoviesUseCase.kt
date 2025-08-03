package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository

class GetCollectionMoviesUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionId: Int
    ) = collectionsRepository.getCollectionMovies(
        collectionId
    )
}