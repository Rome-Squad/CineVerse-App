package com.giraffe.media.movie.usecase


import com.giraffe.media.movie.repository.MoviesRepository
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
            val expectedRecentlyReleased = fakeMovies.filter { it.recentReleasedAt != null }
            val page = 1
            val limit = 10
            coEvery {
                repository.getRecentlyReleasedMovies(page, limit)
            } returns expectedRecentlyReleased

            // When
            val actualMovies = useCase(page, limit)

            // Then
            coVerify(exactly = 1) {
                repository.getRecentlyReleasedMovies(page, limit)
            }
            assertThat(actualMovies).isEqualTo(expectedRecentlyReleased)
        }
}

