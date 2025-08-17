package com.giraffe.media.movie.usecase.recentlyViewed

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


class GetRecentlyViewedMoviesUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: ObserveRecentlyViewedMoviesUseCase = ObserveRecentlyViewedMoviesUseCase(repository)

    @Test
    fun `invoke should call getRecentlyViewed on repository`() = runTest {
        // given
        coEvery {
            repository.observeRecentlyViewed(
                any(),
                any()
            )
        } returns flow { emit(emptyList()) }

        // when
        useCase.invoke(page = page, pageSize = limit)

        // then
        coVerify(exactly = 1) { repository.observeRecentlyViewed(any(), any()) }
    }


    @Test
    fun `given recently viewed movies, when invoke is called, then return movie list`() = runTest {
        // given
        val expectedMovies = flow { emit(fakeMovies) }
        coEvery { repository.observeRecentlyViewed(any(), any()) } returns expectedMovies

        // when
        val result = useCase.invoke(page = page, pageSize = limit)

        // then
        assertThat(result).isEqualTo(expectedMovies)
    }

}