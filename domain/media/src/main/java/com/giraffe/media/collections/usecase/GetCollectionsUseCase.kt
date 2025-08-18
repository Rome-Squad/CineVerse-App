package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.user.exception.AccessDeniedException
import com.giraffe.user.usecase.GetUserUseCase
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val getUserUseCase: GetUserUseCase,
    private val isLoggedIn: IsLoggedInByAccountUseCase
) {
    suspend operator fun invoke(): Flow<List<Collection>> {
        val isLoggedIn = isLoggedIn()
        if (!isLoggedIn) {
            throw AccessDeniedException()
        }

        val accountId = getUserUseCase().id
        return collectionsRepository.getCollections(accountId)
    }
}