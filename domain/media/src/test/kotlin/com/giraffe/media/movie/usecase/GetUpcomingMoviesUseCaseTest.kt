package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUpcomingMoviesUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetUpcomingMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetUpcomingMoviesUseCase(repository)
    }

    @Test
    fun `given upcoming movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedMovies = fakeMovies
        val page = 1
        val limit = 10

        coEvery { repository.getUpcoming(page, limit) } returns expectedMovies

        // When
        val actualMovies = useCase(page, limit)

        // Then
        coVerify(exactly = 1) { repository.getUpcoming(page, limit) }
        assertThat(actualMovies).isEqualTo(expectedMovies)
    }
}
