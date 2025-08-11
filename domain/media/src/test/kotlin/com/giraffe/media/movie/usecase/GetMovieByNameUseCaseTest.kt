package com.giraffe.media.movie.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMovieByNameUseCaseTest {

    private var repository: MovieRepository = mockk(relaxed = true)
    private var useCase: GetMoviesByNameUseCase = GetMoviesByNameUseCase(repository)

    private val movieAction = fakeMovie(
        id = 1,
        title = "Action Movie",
        genresID = listOf(28),
        popularity = 0f
    )
    private val movieSciFi = fakeMovie(
        id = 2,
        title = "Sci-Fi Movie",
        genresID = listOf(878),
        popularity = 0f
    )
    private val name = "test"

    @Test
    fun `invoke should call getByName on repository`() = runTest {
        // Given
        coEvery { repository.getByName(any(), any()) } returns emptyList()

        // When
        useCase(name, page)

        // Then
        coVerify(exactly = 1) { repository.getByName(any(), any()) }
    }

    @Test
    fun `invoke should call getGenres on repository`() = runTest {
        // Given
        coEvery { repository.getGenres() } returns emptyList()

        // When
        useCase(name, page)

        // Then
        coVerify(exactly = 1) { repository.getGenres() }
    }

    @Test
    fun `when genres list is empty should return unsorted search results`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getGenres() } returns emptyList()

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).isEqualTo(searchResults)
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }

    @Test
    fun `when top genre rank is zero should return unsorted search results`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        val genreWithZeroRank = Genre(id = 878, title = "Sci-Fi", rank = 0)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getGenres() } returns listOf(genreWithZeroRank)

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).isEqualTo(searchResults)
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }

    @Test
    fun `when genres exist with rank should return movies sorted by top genre`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        val favoriteGenre = Genre(id = 878, title = "Sci-Fi", rank = 1)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getGenres() } returns listOf(favoriteGenre)

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).containsExactly(movieSciFi, movieAction).inOrder()
    }

    @Test
    fun `when top genre exists but no movie matches it should return unsorted list`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        val unmatchedGenre = Genre(id = 999, title = "Unknown", rank = 1)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getGenres() } returns listOf(unmatchedGenre)

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).isEqualTo(searchResults)
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }
}