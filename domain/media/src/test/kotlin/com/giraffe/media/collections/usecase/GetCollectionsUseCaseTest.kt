package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.fake.createFakeCollection
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.entity.Collection
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCollectionsUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var getCollectionsUseCase: GetCollectionsUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk()
        getCollectionsUseCase = GetCollectionsUseCase(collectionsRepository)
    }

    @Test
    fun `should return list of collections when invoked`() = runTest {
        // Given
        val expectedCollections = listOf(
            createFakeCollection(id = 1, name = "Favorites"),
            createFakeCollection(id = 2, name = "Watch Later")
        )
        coEvery { collectionsRepository.getCollections() } returns expectedCollections

        // When
        val result: List<Collection> = getCollectionsUseCase()

        // Then
        assertThat(result).isEqualTo(expectedCollections)
    }
}
