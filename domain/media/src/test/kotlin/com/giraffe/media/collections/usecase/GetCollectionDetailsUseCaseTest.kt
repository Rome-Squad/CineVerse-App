package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.fake.createFakeCollection
import com.giraffe.media.collections.repository.CollectionsRepository
import com.google.common.truth.Truth.assertThat
import com.giraffe.media.collections.entity.Collection
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCollectionDetailsUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var getCollectionDetailsUseCase: GetCollectionDetailsUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk()
        getCollectionDetailsUseCase = GetCollectionDetailsUseCase(collectionsRepository)
    }

    @Test
    fun `should return collection details when collectionId is valid`() = runTest {
        // Given
        val collectionId = 5
        val expectedCollection = createFakeCollection(id = collectionId)
        coEvery { collectionsRepository.getCollectionDetails(collectionId) } returns expectedCollection

        // When
        val result: Collection = getCollectionDetailsUseCase(collectionId)

        // Then
        assertThat(result).isEqualTo(expectedCollection)
    }
}
