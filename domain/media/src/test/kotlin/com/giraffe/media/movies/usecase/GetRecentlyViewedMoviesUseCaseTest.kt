package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GetRecentlyViewedMoviesUseCaseTest {
    private lateinit var repository: MoviesRepository

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
    }


    @Test
    fun `should returns recently watched movies`() = runTest {
        val expectedMovies = flow {
            emit(fakeMovies)
        }

        coEvery { repository.getRecentlyViewedMovies() } returns expectedMovies

        val useCase = GetRecentlyViewedMoviesUseCase(repository)

        val result = useCase()

        coVerify { repository.getRecentlyViewedMovies() }
        assertThat(result).isEqualTo(expectedMovies)

    }

}