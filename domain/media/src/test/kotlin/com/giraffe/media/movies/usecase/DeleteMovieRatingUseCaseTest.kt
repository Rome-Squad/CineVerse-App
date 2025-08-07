package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteMovieRatingUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: DeleteMovieRatingUseCase

    @Before
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
        coVerify { repository.deleteMovieRating(movieId) }
    }
}
