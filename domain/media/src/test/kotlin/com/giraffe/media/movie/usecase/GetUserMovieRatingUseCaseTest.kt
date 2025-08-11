package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetUserMovieRatingUseCaseTest {
    private lateinit var repository: MovieRepository
    private lateinit var getUserMovieRatingUseCase: GetUserMovieRatingUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        getUserMovieRatingUseCase = GetUserMovieRatingUseCase(repository)
    }

    @Test
    fun `invoke should call getUserRatedById on repository`() = runTest {
        // given
        coEvery { repository.getUserRatedById(any()) } returns 5.0f

        // when
        getUserMovieRatingUseCase(movieId)

        // then
        coVerify(exactly = 1) { repository.getUserRatedById(any()) }
    }

    @Test
    fun `invoke should return rating from repository`() = runTest {
        // given
        val expectedRating = 8.5f
        coEvery { repository.getUserRatedById(movieId) } returns expectedRating

        // when
        val result = getUserMovieRatingUseCase(movieId)

        // then
        assertThat(result).isEqualTo(expectedRating)
    }
}