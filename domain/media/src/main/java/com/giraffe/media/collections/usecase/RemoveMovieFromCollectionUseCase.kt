package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository

class RemoveMovieFromCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionId: Int,
        movieId: Int
    ) = collectionsRepository.removeMovieFromCollection(
        collectionId = collectionId,
        movieId = movieId
    )
}