package com.giraffe.media.movie.usecase


import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecentlyReleasedMoviesUseCaseTest {

    private lateinit var repository: MovieRepository
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
            coEvery {
                repository.getRecentlyReleased(page, limit)
            } returns expectedRecentlyReleased

            // When
            val actualMovies = useCase(page, limit)

            // Then
            coVerify(exactly = 1) {
                repository.getRecentlyReleased(page, limit)
            }
            assertThat(actualMovies).isEqualTo(expectedRecentlyReleased)
        }
}

