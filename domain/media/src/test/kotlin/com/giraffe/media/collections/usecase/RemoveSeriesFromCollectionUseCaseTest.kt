package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveSeriesFromCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var removeSeriesFromCollectionUseCase: RemoveSeriesFromCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        removeSeriesFromCollectionUseCase = RemoveSeriesFromCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `should call repository with correct collectionId and seriesId when invoked`() = runTest {
        // Given
        val collectionId = 8
        val seriesId = 22

        // When
        removeSeriesFromCollectionUseCase(collectionId, seriesId)

        // Then
        coVerify {
            collectionsRepository.removeSeriesFromCollection(
                collectionId = collectionId,
                seriesId = seriesId
            )
        }
    }
}
