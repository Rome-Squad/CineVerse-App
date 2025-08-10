package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetRecommendedMovieUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getRecommendedMoviesUseCase = GetRecommendedMoviesUseCase(repository)
    }

    @Test
    fun `invoke should call getRecommendedMovie on repository with correct id and page`() =
        runTest {
            // given
            val movieId = 1
            val page = 2
            val limit = 10
            coEvery { repository.getRecommended(movieId, page, limit) } returns emptyList()

            // when
            getRecommendedMoviesUseCase(movieId, page, limit)

            // then
            coVerify(exactly = 1) { repository.getRecommended(movieId, page, limit) }
        }

    @Test
    fun `invoke should return list of recommended movies from repository`() = runTest {
        // given
        val movieId = 1
        val page = 1
        val limit = 10
        val expectedMovies = listOf(
            fakeMovie(id = 101, title = "Recommended Movie 1"),
            fakeMovie(id = 102, title = "Recommended Movie 2")
        )
        coEvery { repository.getRecommended(movieId, page, limit) } returns expectedMovies

        // when
        val result = getRecommendedMoviesUseCase(movieId, page, limit)

        // then
        assertThat(result).isEqualTo(expectedMovies)
    }
}