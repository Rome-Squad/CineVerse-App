package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository

class AddMovieToCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collectionId: Int,
        movieId: Int
    ) = collectionsRepository.addMovieToCollection(
        collectionId = collectionId,
        movieId = movieId
    )
}