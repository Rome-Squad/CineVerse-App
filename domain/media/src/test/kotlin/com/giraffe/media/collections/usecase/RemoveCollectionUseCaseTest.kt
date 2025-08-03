package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var removeCollectionUseCase: RemoveCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        removeCollectionUseCase = RemoveCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `should call repository with correct collectionId when invoked`() = runTest {
        // Given
        val collectionId = 99

        // When
        removeCollectionUseCase(collectionId)

        // Then
        coVerify { collectionsRepository.removeCollection(collectionId) }
    }
}
