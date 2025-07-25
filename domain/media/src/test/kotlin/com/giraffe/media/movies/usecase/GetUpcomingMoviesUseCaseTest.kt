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

class GetUpcomingMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetUpcomingMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetUpcomingMoviesUseCase(repository)
    }

    @Test
    fun `given upcoming movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedMovies = listOf(
            Movie(
                id = 4,
                title = "Avatar 3",
                description = "The next Pandora adventure",
                rating = 0.0f,
                duration = null,
                posterUrl = "https://example.com/avatar3.jpg",
                genresID = listOf(1, 4),
                releaseYear = LocalDate(2025, 12, 20)
            )
        )

        coEvery { repository.getUpcomingMovies(1) } returns expectedMovies

        // When
        val actualMovies = useCase(1)

        // Then
        coVerify(exactly = 1) { repository.getUpcomingMovies(1) }
        assertThat(actualMovies).isEqualTo(expectedMovies)
    }
}
