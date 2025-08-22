package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import javax.inject.Inject

class GetCollectionsByMovieIdUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        movieId: Int
    ) = collectionsRepository.getCollectionsByMovieId(movieId)
        .map { it.id }
}
