package com.giraffe.media.collections.usecase


import com.giraffe.media.collections.repository.CollectionsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.giraffe.media.collections.util.createFakeCollection
import io.mockk.coVerify


class GetCollectionsByMovieIdUseCaseTest {

    private val repository: CollectionsRepository = mockk()
    private val useCase = GetCollectionsByMovieIdUseCase(repository)

    @Test
    fun `given movieId when invoke then returns collection ids`() = runTest {
        // Given
        val movieId = 123
        val collections = listOf(
            createFakeCollection(id = 1, name = "Collection One"),
            createFakeCollection(id = 2, name = "Collection Two")
        )
        coEvery { repository.getCollectionsByMovieId(movieId) } returns collections

        // When
        val result = useCase(movieId)

        // Then
        assertThat(result).containsExactly(1, 2)
        coVerify(exactly = 1) { repository.getCollectionsByMovieId(movieId) }
    }

    @Test
    fun `given empty list when invoke then returns empty list`() = runTest {
        // Given
        val movieId = 456
        coEvery { repository.getCollectionsByMovieId(movieId) } returns emptyList()

        // When
        val result = useCase(movieId)

        // Then
        assertThat(result).isEmpty()
        coVerify { repository.getCollectionsByMovieId(movieId) }
    }
}