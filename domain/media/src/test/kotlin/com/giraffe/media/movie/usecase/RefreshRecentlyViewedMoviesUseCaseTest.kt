package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class RefreshRecentlyViewedMoviesUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: RefreshRecentlyViewedMoviesUseCase =
        RefreshRecentlyViewedMoviesUseCase(repository)

    @Test
    fun `invoke should call refreshRecentlyViewedMovies on repository`() = runTest {
        // given
        coEvery { repository.refreshRecentlyViewedMovies() } returns Unit

        // when
        useCase.invoke()

        // then
        coVerify(exactly = 1) { repository.refreshRecentlyViewedMovies() }
    }
}