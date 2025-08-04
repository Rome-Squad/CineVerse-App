package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var clearCollectionUseCase: ClearCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        clearCollectionUseCase = ClearCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `should call repository with correct collectionId when invoked`() = runTest {
        // Given
        val collectionId = 7

        // When
        clearCollectionUseCase(collectionId)

        // Then
        coVerify { collectionsRepository.clearCollection(collectionId) }
    }
}
