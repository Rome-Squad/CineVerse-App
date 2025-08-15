package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


class GetRecentlyViewedMoviesUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: GetRecentlyViewedMoviesUseCase = GetRecentlyViewedMoviesUseCase(repository)

    @Test
    fun `invoke should call getRecentlyViewed on repository`() = runTest {
        // given
        coEvery { repository.getRecentlyViewed(page = page, pageSize = limit) } returns flow {
            emit(
                emptyList()
            )
        }

        // when
        useCase()

        // then
        coVerify(exactly = 1) { repository.getRecentlyViewed(page = page, pageSize = limit) }
    }


    @Test
    fun `given recently viewed movies, when invoke is called, then return movie list`() = runTest {
        // given
        val expectedMovies = flow { emit(fakeMovies) }
        coEvery {
            repository.getRecentlyViewed(
                page = page,
                pageSize = limit
            )
        } returns expectedMovies

        // when
        val result = useCase.invoke()

        // then
        assertThat(result).isEqualTo(expectedMovies)
    }

}