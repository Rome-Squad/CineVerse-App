package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearMoviesCacheUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: ClearMoviesCacheUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ClearMoviesCacheUseCase(repository)
    }

    @Test
    fun `should call clearMovieCache form repository to clear all movies cache`() = runTest {
        // When
        useCase.clearAll()

        // Then
        coVerify(exactly = 1) {
            repository.clearAll()
        }
    }

    @Test
    fun `should call clearMovieCache form repository to clear only recent viewed movies cache`() =
        runTest {
            // When
            useCase.clearRecentlyViewed()

            // Then
            coVerify(exactly = 1) {
                repository.clearRecentlyViewed()
            }
        }

    @Test
    fun `should call clearMovieCache form repository to clear all movies cache exclude recent viewed`() =
        runTest {
            // When
            useCase.clearExceptRecentlyViewed()

            // Then
            coVerify(exactly = 1) {
                repository.clearExceptRecentlyViewed()
            }
        }
}