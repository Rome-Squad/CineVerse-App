package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.util.fakeMovies
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetCollectionMoviesUseCaseTest {

    private val collectionsRepository: CollectionsRepository = mockk()
    private val getCollectionMoviesUseCase = GetCollectionMoviesUseCase(collectionsRepository)

    @Test
    fun `should return list of movies when collectionId is valid`() = runTest {
        // Given
        val collectionId = 3
        val expectedMovies = fakeMovies
        coEvery { collectionsRepository.getCollectionMovies(collectionId) } returns expectedMovies

        // When
        val result: List<Movie> = getCollectionMoviesUseCase(collectionId)

        // Then
        assertThat(result).isEqualTo(expectedMovies)
    }
}
