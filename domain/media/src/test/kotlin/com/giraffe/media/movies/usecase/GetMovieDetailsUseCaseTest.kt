package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
        backdropUrl = null,
        genresID = listOf(1, 2),
        releaseYear = null,
        youtubeVideoId = "",
        popularity = 0f
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