package com.giraffe.media.movie.usecase.recentlyViewed

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SyncRecentlyViewedMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase = SyncRecentlyViewedMoviesUseCase(repository)

    @Test
    fun `invoke should call refreshRecentlyViewedMovies on repository`() = runTest {
        // given
        coEvery { repository.syncRecentlyViewedMovies() } returns Unit

        // when
        useCase.invoke()

        // then
        coVerify(exactly = 1) { repository.syncRecentlyViewedMovies() }
    }
}