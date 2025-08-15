package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.movieId
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetRecommendedMovieUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetRecommendedMoviesUseCase =
        GetRecommendedMoviesUseCase(repository)

    @Test
    fun `invoke should call getRecommended on repository`() = runTest {
        // given
        coEvery { repository.getRecommended(any(), any()) } returns emptyList()

        // when
        useCase(movieId, page)

        // then
        coVerify(exactly = 1) { repository.getRecommended(any(), any()) }
    }

    @Test
    fun `invoke should return list of recommended movies from repository`() = runTest {
        // given
        coEvery { repository.getRecommended(movieId, page) } returns fakeMovies

        // when
        val result = useCase(movieId, page)

        // then
        assertThat(result).isEqualTo(fakeMovies)
    }
}