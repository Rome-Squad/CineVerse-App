package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetUserMovieRatingUseCaseTest {
    private lateinit var repository: MoviesRepository
    private lateinit var getUserMovieRatingUseCase: GetUserMovieRatingUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        getUserMovieRatingUseCase = GetUserMovieRatingUseCase(repository)
    }

    @Test
    fun `invoke should call getUserMovieRating on repository with correct id`() = runTest {
        // given
        coEvery { repository.getUserMovieRating(any()) } returns 5.0f

        // when
        getUserMovieRatingUseCase(1)

        // then
        coVerify(exactly = 1) { repository.getUserMovieRating(1) }
    }

    @Test
    fun `invoke should return rating from repository`() = runTest {
        // given
        val expectedRating = 8.5f
        coEvery { repository.getUserMovieRating(1) } returns expectedRating

        // when
        val result = getUserMovieRatingUseCase(1)

        // then
        assertThat(result).isEqualTo(expectedRating)
    }
}