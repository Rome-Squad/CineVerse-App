package com.giraffe.media.movie.usecase

import com.giraffe.media.entity.Review
import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetMovieReviewsUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var getMovieReviewsUseCase: GetMovieReviewsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        getMovieReviewsUseCase = GetMovieReviewsUseCase(repository)
    }

    @Test
    fun `invoke should call getMovieReviews on repository`() = runTest {
        // given
        coEvery { repository.getReviews(any(),any()) } returns emptyList()

        // when
        getMovieReviewsUseCase(1, 1)

        // then
        coVerify(exactly = 1) { repository.getReviews(1, 1) }
    }

    @Test
    fun `invoke should return list of reviews from repository`() = runTest {
        // given
        val expectedReviews = listOf(
            Review(
                id = "1",
                content = "Great movie! One of the best I've seen this year. The cinematography was stunning.",
                createdAt = LocalDateTime(2025, 7, 10, 20, 30, 0),
                authorName = "John Doe",
                authorUserName = "user1",
                rating = 9,
                authorImageUrl = "https://example.com/avatars/user1.jpg"
            )
        )
        coEvery { repository.getReviews(1, 1) } returns expectedReviews

        // when
        val result = getMovieReviewsUseCase(1, 1)

        // then
        assertThat(result).isEqualTo(expectedReviews)
    }
}