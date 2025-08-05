package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveMovieFromCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var removeMovieFromCollectionUseCase: RemoveMovieFromCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        removeMovieFromCollectionUseCase = RemoveMovieFromCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `should call repository with correct collectionId and movieId when invoked`() = runTest {
        // Given
        val collectionId = 5
        val movieId = 101

        // When
        removeMovieFromCollectionUseCase(collectionId, movieId)

        // Then
        coVerify {
            collectionsRepository.removeMovieFromCollection(
                collectionId = collectionId,
                movieId = movieId
            )
        }
    }
}
