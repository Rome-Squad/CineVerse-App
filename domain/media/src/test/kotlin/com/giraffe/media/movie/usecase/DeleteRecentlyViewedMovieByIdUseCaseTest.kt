package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteRecentlyViewedMovieByIdUseCaseTest {
    private val movieRepository = mockk<MovieRepository>(relaxed = true)
    private val deleteRecentlyViewedMovie = DeleteRecentlyViewedMovieByIdUseCase(movieRepository)

    @Test
    fun `invoke should call deleteRecentlyViewedMovieById on repository`() = runTest {
        val movieId = 1
        deleteRecentlyViewedMovie(movieId)
        coVerify(exactly = 1) { movieRepository.deleteRecentlyViewedMovieById(movieId) }
    }
}