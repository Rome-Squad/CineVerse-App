package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetRecommendedMovieUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var getRecommendedMovieUseCase: GetRecommendedMovieUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getRecommendedMovieUseCase = GetRecommendedMovieUseCase(repository)
    }

    @Test
    fun `invoke should call getRecommendedMovie on repository with correct id and page`() =
        runTest {
            // given
            val movieId = 1
            val page = 2
            coEvery { repository.getRecommendedMovie(movieId, page) } returns emptyList()

            // when
            getRecommendedMovieUseCase(movieId, page)

            // then
            coVerify(exactly = 1) { repository.getRecommendedMovie(movieId, page) }
        }

    @Test
    fun `invoke should return list of recommended movies from repository`() = runTest {
        // given
        val movieId = 1
        val page = 1
        val expectedMovies = listOf(
            fakeMovie(id = 101, title = "Recommended Movie 1"),
            fakeMovie(id = 102, title = "Recommended Movie 2")
        )
        coEvery { repository.getRecommendedMovie(movieId, page) } returns expectedMovies

        // when
        val result = getRecommendedMovieUseCase(movieId, page)

        // then
        assertThat(result).isEqualTo(expectedMovies)
    }
}