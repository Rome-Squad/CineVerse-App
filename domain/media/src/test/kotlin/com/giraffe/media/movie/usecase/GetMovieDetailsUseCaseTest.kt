package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetMovieDetailsUseCaseTest {

    private var repository: MovieRepository = mockk(relaxed = true)
    private var getMovieDetailsUseCase: GetMovieDetailsUseCase = GetMovieDetailsUseCase(repository)

    private val fakeMovie = fakeMovie(
        id = movieId,
        title = "Test Movie",
    )

    @Test
    fun `invoke should call getDetails on repository`() = runTest {
        // given
        coEvery { repository.getDetails(any()) } returns fakeMovie

        // when
        getMovieDetailsUseCase(movieId)

        // then
        coVerify(exactly = 1) { repository.getDetails(any()) }
    }

    @Test
    fun `invoke should return movie details from repository`() = runTest {
        // given
        coEvery { repository.getDetails(movieId) } returns fakeMovie

        // when
        val result = getMovieDetailsUseCase(movieId)

        // then
        assertThat(result).isEqualTo(fakeMovie)
    }
}