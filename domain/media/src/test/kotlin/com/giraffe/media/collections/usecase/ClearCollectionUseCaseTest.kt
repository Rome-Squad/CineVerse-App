package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ClearCollectionUseCaseTest {

    private val collectionsRepository: CollectionsRepository = mockk(relaxed = true)
    private val clearCollectionUseCase = ClearCollectionUseCase(collectionsRepository)

    @Test
    fun `should call repository with correct collectionId when invoked`() = runTest {
        val collectionId = 7

        clearCollectionUseCase(collectionId)

        coVerify { collectionsRepository.clearCollection(collectionId) }
    }
}
