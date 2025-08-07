package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteMovieUseCaseTest {
    private lateinit var repository: MoviesRepository
    private lateinit var useCase: DeleteMovieUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteMovieUseCase(repository)
    }

    @Test
    fun `invoke should call deleteMovieById method on repository`() = runTest {
        // Given
        val movieId = 101

        // When
        useCase(movieId)

        // Then
        coVerify { repository.deleteMovieById(movieId) }
    }
}