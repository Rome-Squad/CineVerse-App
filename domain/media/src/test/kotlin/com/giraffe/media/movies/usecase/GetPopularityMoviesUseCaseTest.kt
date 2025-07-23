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

class GetPopularityMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetPopularityMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetPopularityMoviesUseCase(repository)
    }

    @Test
    fun `given popular movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedMovies = listOf(
            Movie(
                id = 1,
                title = "Inception",
                description = "A mind-bending thriller",
                rating = 8.8f,
                duration = 148,
                posterUrl = "https://example.com/inception.jpg",
                genresID = listOf(1, 2),
                releaseYear = LocalDate(2024, 4, 10)
            ),
            Movie(
                id = 2,
                title = "Interstellar",
                description = "Exploring the universe",
                rating = 8.6f,
                duration = 169,
                posterUrl = "https://example.com/interstellar.jpg",
                genresID = listOf(2, 3),
                releaseYear = LocalDate(2023, 4, 5)
            )
        )

        coEvery { repository.getPopularityMovies() } returns expectedMovies

        // When
        val actualMovies = useCase()

        // Then
        coVerify(exactly = 1) { repository.getPopularityMovies() }
        assertThat(actualMovies).isEqualTo(expectedMovies)
    }
}
