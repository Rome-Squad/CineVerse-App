package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteMovieUseCaseTest {
    private var repository: MovieRepository = mockk(relaxed = true)
    private var useCase: DeleteMovieUseCase = DeleteMovieUseCase(repository)

    @Test
    fun `invoke should call deleteById method on repository`() = runTest {
        // When
        useCase(movieId)

        // Then
        coVerify { repository.deleteById(movieId) }
    }
}