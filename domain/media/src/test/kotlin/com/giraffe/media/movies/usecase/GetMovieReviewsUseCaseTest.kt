package com.giraffe.media.movies.usecase

import com.google.common.truth.Truth.assertThat
import com.giraffe.media.movies.entity.MovieReview
import com.giraffe.media.movies.entity.MovieReviewAuthor
import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetMovieReviewsUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var getMovieReviewsUseCase: GetMovieReviewsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        getMovieReviewsUseCase = GetMovieReviewsUseCase(repository)
    }

    @Test
    fun `invoke should call getMovieReviews on repository`() = runTest {
        // given
        coEvery { repository.getMovieReviews(any()) } returns emptyList()

        // when
        getMovieReviewsUseCase(1, 1, 20)

        // then
        coVerify(exactly = 1) { repository.getMovieReviews(1) }
    }

    @Test
    fun `invoke should return list of reviews from repository`() = runTest {
        // given
        val expectedReviews = listOf(
            MovieReview(
                id = "1",
                content = "Great movie! One of the best I've seen this year. The cinematography was stunning.",
                createdAt = LocalDateTime(2025, 7, 10, 20, 30, 0),
                updatedAt = LocalDateTime(2025, 7, 10, 20, 35, 0),
                url = "https://www.themoviedb.org/review/1",
                author = MovieReviewAuthor(
                    name = "John Doe",
                    username = "user1",
                    avatarImage = "https://example.com/avatars/user1.jpg",
                    rating = 9
                )
            )
        )
        coEvery { repository.getMovieReviews(1) } returns expectedReviews

        // when
        val result = getMovieReviewsUseCase(1, 1, 20)

        // then
        assertThat(result).isEqualTo(expectedReviews)
    }
}