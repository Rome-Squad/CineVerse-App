package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.movieId
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteMovieRatingUseCaseTest {

    private var repository: MovieRepository = mockk(relaxUnitFun = true)
    private var useCase: DeleteMovieRatingUseCase = DeleteMovieRatingUseCase(repository)

    @Test
    fun `invoke should call deleteRating method on repository`() = runTest {
        // When
        useCase(movieId)

        // Then
        coVerify { repository.deleteRating(movieId) }
    }
}
