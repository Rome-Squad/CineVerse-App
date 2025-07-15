package com.giraffe.movies.usecase

import com.google.common.truth.Truth.assertThat
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetMovieDetailsUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private val fakeMovie = Movie(
        id = 1,
        title = "Test Movie",
        description = "",
        rating = 0.0f,
        duration = null,
        posterUrl = null,
        genresID = listOf(1,2),
        releaseYear = null
    )

    @BeforeEach
    fun setup() {
        repository = mockk()
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `invoke should call getMovieDetails on repository with correct id`() = runTest {
        // given
        coEvery { repository.getMovieDetails(any()) } returns fakeMovie

        // when
        getMovieDetailsUseCase(1)

        // then
        coVerify(exactly = 1) { repository.getMovieDetails(1) }
    }

    @Test
    fun `invoke should return movie details from repository`() = runTest {
        // given
        coEvery { repository.getMovieDetails(1) } returns fakeMovie

        // when
        val result = getMovieDetailsUseCase(1)

        // then
        assertThat(result).isEqualTo(fakeMovie)
    }
}