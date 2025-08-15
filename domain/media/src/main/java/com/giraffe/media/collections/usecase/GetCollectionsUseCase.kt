package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.user.exception.AccessDeniedException
import com.giraffe.user.usecase.GetUserUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): Flow<List<Collection>> {
        val accountId = getUserUseCase().first()?.id ?: throw AccessDeniedException()
        return collectionsRepository.getCollections(accountId)
    }
}