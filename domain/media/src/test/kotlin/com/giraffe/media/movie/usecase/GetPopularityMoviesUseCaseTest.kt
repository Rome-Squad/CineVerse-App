package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPopularityMoviesUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `given popular movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedPopularityMovies = fakeMovies.filter { it.popularity > 0 }
        val page = 1
        val limit = 10
        coEvery { repository.getPopular(page, limit) } returns expectedPopularityMovies

        // When
        val actualMovies = useCase(page,limit)

        // Then
        coVerify(exactly = 1) { repository.getPopular(page, limit) }
        assertThat(actualMovies).isEqualTo(expectedPopularityMovies)
    }
}
