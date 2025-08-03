package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddSeriesToCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var addSeriesToCollectionUseCase: AddSeriesToCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        addSeriesToCollectionUseCase = AddSeriesToCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `should call repository with correct collectionId and seriesId when invoked`() = runTest {
        // Given
        val collectionId = 10
        val seriesId = 55

        // When
        addSeriesToCollectionUseCase(collectionId, seriesId)

        // Then
        coVerify {
            collectionsRepository.addSeriesToCollection(
                collectionId = collectionId,
                seriesId = seriesId
            )
        }
    }
}
