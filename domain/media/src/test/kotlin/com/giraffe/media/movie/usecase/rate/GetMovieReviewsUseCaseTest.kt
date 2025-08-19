package com.giraffe.media.movie.usecase.rate

import com.giraffe.media.entity.Review
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test

class GetMovieReviewsUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var getMovieReviewsUseCase: GetMovieReviewsUseCase = GetMovieReviewsUseCase(repository)
    private val reviewId = 1

    @Test
    fun `invoke should call getReviews on repository`() = runTest {
        // given
        coEvery { repository.getReviews(any(), any()) } returns emptyList()

        // when
        getMovieReviewsUseCase(reviewId, page)

        // then
        coVerify(exactly = 1) { repository.getReviews(any(), any()) }
    }

    @Test
    fun `invoke should return list of reviews from repository`() = runTest {
        // given
        val expectedReviews = listOf(
            Review(
                id = reviewId.toString(),
                content = "Great movie! One of the best I've seen this year. The cinematography was stunning.",
                createdAt = LocalDateTime(2025, 7, 10, 20, 30, 0),
                authorName = "John Doe",
                authorUserName = "user1",
                rating = 9,
                authorImageUrl = "https://example.com/avatars/user1.jpg"
            )
        )
        coEvery { repository.getReviews(reviewId, page) } returns expectedReviews

        // when
        val result = getMovieReviewsUseCase(reviewId, page)

        // then
        assertThat(result).isEqualTo(expectedReviews)
    }
}