package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
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
        id = 1,
        title = "Action Movie",
        description = "",
        rating = 7.0f,
        duration = 120,
        posterUrl = "",
        backdropUrl = "",
        youtubeVideoId = "",
        genresID = listOf(28),
        releaseYear = LocalDate(2022, 1, 1),
        popularity = 0f
    )
    private val movieSciFi = Movie(
        id = 2,
        title = "Sci-Fi Movie",
        description = "",
        rating = 8.0f,
        duration = 130,
        posterUrl = "",
        backdropUrl = "",
        youtubeVideoId = "",
        genresID = listOf(878),
        releaseYear = LocalDate(2023, 1, 1),
        popularity = 0f
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = SearchMovieByNameUseCase(repository)
    }

    @Test
    fun `invoke() should call search and genres methods on repository`() = runTest {
        // Given
        coEvery { repository.searchMovieByName(any(), any()) } returns emptyList()
        coEvery { repository.getMoviesGenres() } returns emptyList()

        // When
        useCase("test", 1)

        // Then
        coVerify(exactly = 1) { repository.searchMovieByName("test", 1) }
        coVerify(exactly = 1) { repository.getMoviesGenres() }
    }

    @Test
    fun `when genres list is empty should return unsorted search results`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        coEvery { repository.searchMovieByName(any(), any()) } returns searchResults
        coEvery { repository.getMoviesGenres() } returns emptyList()

        // When
        val result = useCase("test", 1)

        // Then
        assertThat(result).isEqualTo(searchResults)
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }

    @Test
    fun `when top genre rank is zero should return unsorted search results`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        val genreWithZeroRank = Genre(id = 878, title = "Sci-Fi", rank = 0)
        coEvery { repository.searchMovieByName(any(), any()) } returns searchResults
        coEvery { repository.getMoviesGenres() } returns listOf(genreWithZeroRank)

        // When
        val result = useCase("test", 1)

        // Then
        assertThat(result).isEqualTo(searchResults)
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }

    @Test
    fun `when genres exist with rank should return movies sorted by top genre`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        val favoriteGenre = Genre(id = 878, title = "Sci-Fi", rank = 1)
        coEvery { repository.searchMovieByName(any(), any()) } returns searchResults
        coEvery { repository.getMoviesGenres() } returns listOf(favoriteGenre)

        // When
        val result = useCase("test", 1)

        // Then
        assertThat(result).containsExactly(movieSciFi, movieAction).inOrder()
    }
}