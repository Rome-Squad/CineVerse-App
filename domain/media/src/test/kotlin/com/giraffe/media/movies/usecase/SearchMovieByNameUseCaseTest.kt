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

class SearchMovieByNameUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: SearchMovieByNameUseCase

    private val movieAction = Movie(
        id = 1, title = "Action Movie", description = "", rating = 7.0f,
        duration = 120, posterUrl = "", genresID = listOf(28),
        releaseYear = LocalDate(2022, 1, 1)
    )
    private val movieSciFi = Movie(
        id = 2, title = "Sci-Fi Movie", description = "", rating = 8.0f,
        duration = 130, posterUrl = "", genresID = listOf(878),
        releaseYear = LocalDate(2023, 1, 1)
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = SearchMovieByNameUseCase(repository)
    }

    @Test
    fun `invoke should call search and preferences on repository`() = runTest {
        // Given
        coEvery { repository.searchMovieByName(any()) } returns emptyList()
        coEvery { repository.getUserGenrePreferences() } returns emptyList()

        // When
        useCase("test")

        // Then
        coVerify(exactly = 1) { repository.searchMovieByName("test") }
        coVerify(exactly = 1) { repository.getUserGenrePreferences() }
    }

    @Test
    fun `when no genre preferences should return unsorted search results`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        coEvery { repository.searchMovieByName(any()) } returns searchResults
        coEvery { repository.getUserGenrePreferences() } returns emptyList()

        // When
        val result = useCase("test")

        // Then
        assertThat(result).isEqualTo(searchResults)
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }

    @Test
    fun `when genre preferences exist should return movies sorted by top preference`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        val userPreference = GenrePreference(
            genreId = 878,
            count = 3
        )
        coEvery { repository.searchMovieByName(any()) } returns searchResults
        coEvery { repository.getUserGenrePreferences() } returns listOf(userPreference)

        // When
        val result = useCase("test")

        // Then
        assertThat(result).hasSize(2)
        assertThat(result).containsExactly(movieSciFi, movieAction).inOrder()
    }
}