package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPopularityMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetPopularityMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetPopularityMoviesUseCase(repository)
    }

    @Test
    fun `given popular movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedPopularityMovies = fakeMovies.filter { it.popularity > 0 }
        val page = 1
        val limit = 10
        coEvery { repository.getPopularityMovies(page, limit) } returns expectedPopularityMovies

        // When
        val actualMovies = useCase(page,limit)

        // Then
        coVerify(exactly = 1) { repository.getPopularityMovies(page, limit) }
        assertThat(actualMovies).isEqualTo(expectedPopularityMovies)
    }
}
