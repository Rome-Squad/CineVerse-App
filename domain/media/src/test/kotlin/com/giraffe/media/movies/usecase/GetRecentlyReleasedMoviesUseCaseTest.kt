package com.giraffe.media.movies.usecase


import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecentlyReleasedMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetRecentlyReleasedMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetRecentlyReleasedMoviesUseCase(repository)
    }

    @Test
    fun `given recently released movies, when invoke is called, then return movie list`() =
        runTest {
            // Given
            val expectedRecentlyReleased = fakeMovies
            val page = 1
            val limit = 10
            val useRemoteOnly = false
            coEvery {
                repository.getRecentlyReleasedMovies(page, limit, useRemoteOnly)
            } returns expectedRecentlyReleased

            // When
            val actualMovies = useCase(page, limit, useRemoteOnly)

            // Then
            coVerify(exactly = 1) {
                repository.getRecentlyReleasedMovies(page, limit, useRemoteOnly)
            }
            assertThat(actualMovies).isEqualTo(expectedRecentlyReleased)
        }
}

