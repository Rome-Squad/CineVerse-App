package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.exception.ValidationException

class AddCollectionUseCase(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(
        collection: Collection
    ) {
        if (collection.name.isBlank())
            throw ValidationException("Collection name cannot be blank")

        collectionsRepository.addCollection(
            collection
        )
    }

}