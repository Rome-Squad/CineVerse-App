package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import javax.inject.Inject

class AddMovieToCollectionUseCase @Inject constructor(
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