package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteMovieRatingUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: DeleteMovieRatingUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteMovieRatingUseCase(repository)
    }

    @Test
    fun `invoke should call deleteMovieRating method on repository`() = runTest {
        // Given
        val movieId = 101

        // When
        useCase(movieId)

        // Then
        coVerify { repository.deleteRating(movieId) }
    }
}
