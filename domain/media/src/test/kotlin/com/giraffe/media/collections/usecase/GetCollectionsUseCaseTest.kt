package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.util.createFakeCollection
import com.giraffe.user.usecase.GetUserUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetCollectionsUseCaseTest {

    private val collectionsRepository: CollectionsRepository = mockk()
    private val getUserUseCase: GetUserUseCase = mockk()
    private val isLoggedInUseCase: IsLoggedInUseCase = mockk()
    private val getCollectionsUseCase =
        GetCollectionsUseCase(collectionsRepository, getUserUseCase, isLoggedInUseCase)

    @Test
    fun `should return list of collections when GetCollectionsUseCase invoked`() = runTest {
        // Given
        val expectedCollections = listOf(
            createFakeCollection(id = 1, name = "Favorites"),
            createFakeCollection(id = 2, name = "Watch Later")
        )

        coEvery { isLoggedInUseCase() } returns true
        coEvery { getUserUseCase().id } returns 111
        every { collectionsRepository.getCollections(111) } returns flowOf(expectedCollections)

        // When
        val result = getCollectionsUseCase().first()

        // Then
        assertThat(result).isEqualTo(expectedCollections)
    }
}
