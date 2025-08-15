package com.giraffe.media.series.usecase

import com.giraffe.media.movie.util.movieId
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetUserSeriesRatingUseCaseTest {
    private var repository: SeriesRepository = mockk()
    private var getUserRatingUseCase: GetUserSeriesRatingUseCase =
        GetUserSeriesRatingUseCase(repository)
    private val expectedRating = 8.5f

    @Test
    fun `invoke should call getUserRatedById on repository`() = runTest {
        // given
        coEvery { repository.getUserRating(any()) } returns expectedRating

        // when
        getUserRatingUseCase(movieId)

        // then
        coVerify(exactly = 1) { repository.getUserRating(any()) }
    }

    @Test
    fun `invoke should return rating from repository`() = runTest {
        // given
        coEvery { repository.getUserRating(movieId) } returns expectedRating

        // when
        val result = getUserRatingUseCase(movieId)

        // then
        assertThat(result).isEqualTo(expectedRating)
    }
}