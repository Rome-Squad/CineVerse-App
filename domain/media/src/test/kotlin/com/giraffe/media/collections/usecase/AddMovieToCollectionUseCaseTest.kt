package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddMovieToCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var addMovieToCollectionUseCase: AddMovieToCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        addMovieToCollectionUseCase = AddMovieToCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `should call repository with correct collectionId and movieId when invoked`() = runTest {
        // Given
        val collectionId = 42
        val movieId = 101

        // When
        addMovieToCollectionUseCase(collectionId, movieId)

        // Then
        coVerify {
            collectionsRepository.addMovieToCollection(
                collectionId = collectionId,
                movieId = movieId
            )
        }
    }
}
