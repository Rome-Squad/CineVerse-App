package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearMoviesCacheUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: ClearMoviesCacheUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ClearMoviesCacheUseCase(repository)
    }

    @Test
    fun `should call clearMovieCache form repository to clear all movies cache`() = runTest {
        // When
        useCase(
            clearOnlyRecentlyViewed = false,
            excludeRecentlyViewed = false
        )

        // Then
        coVerify {
            repository.clearMovieCache(
                clearOnlyRecentlyViewed = false,
                excludeRecentlyViewed = false
            )
        }
    }

    @Test
    fun `should call clearMovieCache form repository to clear only recent viewed movies cache`() = runTest {
        // When
        useCase(
            clearOnlyRecentlyViewed = true,
            excludeRecentlyViewed = false
        )

        // Then
        coVerify {
            repository.clearMovieCache(
                clearOnlyRecentlyViewed = true,
                excludeRecentlyViewed = false
            )
        }
    }

    @Test
    fun `should call clearMovieCache form repository to clear all movies cache exclude recent viewed`() = runTest {
        // When
        useCase(
            clearOnlyRecentlyViewed = false,
            excludeRecentlyViewed = true
        )

        // Then
        coVerify {
            repository.clearMovieCache(
                clearOnlyRecentlyViewed = false,
                excludeRecentlyViewed = true
            )
        }
    }
}