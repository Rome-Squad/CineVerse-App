package com.giraffe.movies.usecase

import com.giraffe.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearCacheUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: ClearCacheUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ClearCacheUseCase(repository)
    }

    @Test
    fun `should call repository to clear cache`() = runTest {
        // When
        useCase.execute()

        // Then
        coVerify { repository.clearCache() }
    }
}