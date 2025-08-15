package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.util.createFakeCollection
import com.giraffe.user.usecase.GetUserUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetCollectionsUseCaseTest {

    private val collectionsRepository: CollectionsRepository = mockk()
    private val getUserUseCase: GetUserUseCase = mockk()
    private val getCollectionsUseCase = GetCollectionsUseCase(collectionsRepository, getUserUseCase)

    @Test
    fun `should return list of collections when GetCollectionsUseCase invoked`() = runTest {
        // Given
        val expectedCollections = listOf(
            createFakeCollection(id = 1, name = "Favorites"),
            createFakeCollection(id = 2, name = "Watch Later")
        )

        coEvery { getUserUseCase().id } returns 111
        coEvery { collectionsRepository.getCollections(111) } returns expectedCollections

        // When
        val result: List<Collection> = getCollectionsUseCase()

        // Then
        assertThat(result).isEqualTo(expectedCollections)
    }
}
