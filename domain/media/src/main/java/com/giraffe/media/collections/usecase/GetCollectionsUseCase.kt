package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.user.usecase.GetUserUseCase
import javax.inject.Inject
import com.giraffe.media.collections.entity.Collection
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): Flow<List<Collection>> {
        val accountId = getUserUseCase().id
        return collectionsRepository.getCollections(accountId)
    }
}