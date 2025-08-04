package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.fake.createFakeMovie
import com.giraffe.media.movies.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCollectionMoviesUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var getCollectionMoviesUseCase: GetCollectionMoviesUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk()
        getCollectionMoviesUseCase = GetCollectionMoviesUseCase(collectionsRepository)
    }

    @Test
    fun `should return list of movies when collectionId is valid`() = runTest {
        // Given
        val collectionId = 3
        val expectedMovies = listOf(
            createFakeMovie(
                id = 1,
                title = "Inception"
            ),
            createFakeMovie(
                id = 2,
                title = "Interstellar"
            )
        )
        coEvery { collectionsRepository.getCollectionMovies(collectionId) } returns expectedMovies

        // When
        val result: List<Movie> = getCollectionMoviesUseCase(collectionId)

        // Then
        assertThat(result).isEqualTo(expectedMovies)
    }
}
