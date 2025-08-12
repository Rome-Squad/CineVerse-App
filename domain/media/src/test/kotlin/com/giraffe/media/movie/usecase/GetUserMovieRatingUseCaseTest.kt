package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetUserMovieRatingUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var getUserMovieRatingUseCase: GetUserMovieRatingUseCase =
        GetUserMovieRatingUseCase(repository)
    private val expectedRating = 8.5f

    @Test
    fun `invoke should call getUserRatedById on repository`() = runTest {
        // given
        coEvery { repository.getUserRatedById(any()) } returns expectedRating

        // when
        getUserMovieRatingUseCase(movieId)

        // then
        coVerify(exactly = 1) { repository.getUserRatedById(any()) }
    }

    @Test
    fun `invoke should return rating from repository`() = runTest {
        // given
        coEvery { repository.getUserRatedById(movieId) } returns expectedRating

        // when
        val result = getUserMovieRatingUseCase(movieId)

        // then
        assertThat(result).isEqualTo(expectedRating)
    }
}