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
    fun `given recently viewed movies, when invoke is called, then return movie list`() = runTest {
        val expectedMovies = flow {
            emit(fakeMovies)
        }

        coEvery { repository.getRecentlyViewedMovies() } returns expectedMovies

        val result = GetRecentlyViewedMoviesUseCase(repository).invoke()

        coVerify { repository.getRecentlyViewedMovies() }
        assertThat(result).isEqualTo(expectedMovies)

    }

}