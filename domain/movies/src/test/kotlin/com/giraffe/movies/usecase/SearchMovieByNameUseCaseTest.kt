package com.giraffe.movies.usecase

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchMovieByNameUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: SearchMovieByNameUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = SearchMovieByNameUseCase(repository)
    }

    @Test
    fun `should return list of movies matching the name from repository`() = runTest {
        // Given
        val movieName = "Inception"
        val expectedMovies = listOf(
            Movie(
                id = 1,
                title = "Inception",
                description = "A mind-bending thriller",
                rating = 8.8f,
                duration = 148,
                posterUrl = "https://example.com/inception.jpg",
                genresID = listOf(878, 28),
                releaseYear = LocalDate(2010, 7, 16)
            )
        )

        coEvery { repository.searchMovieByName(movieName) } returns expectedMovies

        // When
        val result = useCase(movieName)

        // Then
        coVerify { repository.searchMovieByName(movieName) }
        assertThat(result).isEqualTo(expectedMovies)
    }
}