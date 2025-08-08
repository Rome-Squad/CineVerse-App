package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUpcomingMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetUpcomingMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetUpcomingMoviesUseCase(repository)
    }

    @Test
    fun `given upcoming movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedMovies = fakeMovies.filter { it.upcomingAt != null}
        val page = 1
        val limit = 10

        coEvery { repository.getUpcomingMovies(page, limit) } returns expectedMovies

        // When
        val actualMovies = useCase(page, limit)

        // Then
        coVerify(exactly = 1) { repository.getUpcomingMovies(page, limit) }
        assertThat(actualMovies).isEqualTo(expectedMovies)
    }
}
