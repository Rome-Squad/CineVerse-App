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
    fun `should call repository to clear cache`() = runTest {
        // When
        useCase()

        // Then
        coVerify { repository.clearMovieCache() }
    }
}