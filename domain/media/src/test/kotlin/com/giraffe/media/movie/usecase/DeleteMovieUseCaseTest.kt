package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.usecase.recentlyViewed.DeleteRecentlyViewedMovieByIdUseCase
import com.giraffe.media.movie.util.movieId
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteMovieUseCaseTest {
    private var repository: MovieRepository = mockk(relaxUnitFun = true)
    private var useCase: DeleteRecentlyViewedMovieByIdUseCase = DeleteRecentlyViewedMovieByIdUseCase(repository)

    @Test
    fun `invoke should call deleteById method on repository`() = runTest {
        // When
        useCase(movieId)

        // Then
        coVerify { repository.deleteRecentlyViewedMovieById(movieId) }
    }
}