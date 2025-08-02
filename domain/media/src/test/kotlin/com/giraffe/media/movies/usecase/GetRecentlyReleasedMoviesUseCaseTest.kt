package com.giraffe.media.movies.usecase


import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecentlyReleasedMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetRecentlyReleasedMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetRecentlyReleasedMoviesUseCase(repository)
    }

    @Test
    fun `given recently released movies, when invoke is called, then return movie list`() =
        runTest {
            // Given
            val expectedMovies = listOf(
                Movie(
                    id = 3,
                    title = "Dune: Part Two",
                    description = "A new chapter in the saga",
                    rating = 8.9f,
                    duration = 155,
                    posterUrl = "https://example.com/dune2.jpg",
                    backdropUrl = "https://example.com/dune2.jpg",
                    youtubeVideoId = "abc123",
                    genresID = listOf(4, 5),
                    releaseYear = LocalDate(2024, 3, 1)
                )
            )

            coEvery { repository.getRecentlyReleasedMovies(1) } returns expectedMovies

            // When
            val actualMovies = useCase(1)

            // Then
            coVerify(exactly = 1) { repository.getRecentlyReleasedMovies(1) }
            assertThat(actualMovies).isEqualTo(expectedMovies)
        }
}

