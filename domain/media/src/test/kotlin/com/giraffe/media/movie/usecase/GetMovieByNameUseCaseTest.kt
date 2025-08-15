package com.giraffe.media.movie.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovie
import com.giraffe.media.movie.util.fakeTopGenre
import com.giraffe.media.movie.util.genreWithZeroRank
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMovieByNameUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetMoviesByNameUseCase = GetMoviesByNameUseCase(repository)

    private val movieAction = fakeMovie(
        id = 1,
        title = "Action Movie",
        genresID = listOf(fakeTopGenre.id),
        popularity = 0f
    )
    private val movieSciFi = fakeMovie(
        id = 2,
        title = "Sci-Fi Movie",
        genresID = listOf(genreWithZeroRank.id),
        popularity = 0f
    )
    private val name = "test"

    @Test
    fun `invoke should call getByName on repository`() = runTest {
        // Given
        coEvery { repository.getByName(any(), any()) } returns emptyList()
        coEvery { repository.getTopGenre() } returns null

        // When
        useCase.invoke(name, page)

        // Then
        coVerify(exactly = 1) { repository.getByName(any(), any()) }
    }

    @Test
    fun `invoke should call getTopGenre on repository`() = runTest {
        // Given
        coEvery { repository.getByName(any(), any()) } returns emptyList()
        coEvery { repository.getTopGenre() } returns null

        // When
        useCase(name, page)

        // Then
        coVerify(exactly = 1) { repository.getTopGenre() }
    }

    @Test
    fun `when don't have top genre should return getByName results`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getTopGenre() } returns null

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).isEqualTo(searchResults)
    }

    @Test
    fun `when top genre rank is zero should return sorted search results by top genre`() = runTest {
        // Given
        val searchResults = listOf(fakeMovie, movieSciFi)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getTopGenre() } returns genreWithZeroRank

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).containsExactly(movieSciFi, fakeMovie).inOrder()
    }

    @Test
    fun `when genres exist with rank should return movies sorted by top genre`() = runTest {
        // Given
        val searchResults = listOf(movieSciFi, movieAction)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getTopGenre() } returns fakeTopGenre

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }

    @Test
    fun `when top genre exists but no movie matches it should return unsorted list`() = runTest {
        // Given
        val searchResults = listOf(movieAction, movieSciFi)
        val unmatchedGenre = Genre(id = 999, title = "Unknown", rank = 1)
        coEvery { repository.getByName(name, page) } returns searchResults
        coEvery { repository.getTopGenre() } returns unmatchedGenre

        // When
        val result = useCase(name, page)

        // Then
        assertThat(result).containsExactly(movieAction, movieSciFi).inOrder()
    }
}