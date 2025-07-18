package com.giraffe.media.series.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchSeriesByNameUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var searchSeriesByNameUseCase: SearchSeriesByNameUseCase

    private val seriesAction = fakeSeries(id = 1, name = "Action Series", genreIDs = listOf(10759))
    private val seriesComedy = fakeSeries(id = 2, name = "Comedy Series", genreIDs = listOf(35))

    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        searchSeriesByNameUseCase = SearchSeriesByNameUseCase(seriesRepository)
    }

    @Test
    fun `invoke() should call search and genres methods on repository`() = runTest {
        // Given
        coEvery { seriesRepository.searchSeriesByName(any()) } returns emptyList()
        coEvery { seriesRepository.getSeriesGenres() } returns emptyList()

        // When
        searchSeriesByNameUseCase("test")

        // Then
        coVerify(exactly = 1) { seriesRepository.searchSeriesByName("test") }
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

    @Test
    fun `when genres list is empty should return unsorted search results`() = runTest {
        // Given
        val searchResults = listOf(seriesAction, seriesComedy)
        coEvery { seriesRepository.searchSeriesByName(any()) } returns searchResults
        coEvery { seriesRepository.getSeriesGenres() } returns emptyList()

        // When
        val result = searchSeriesByNameUseCase("test")

        // Then
        assertThat(result).containsExactly(seriesAction, seriesComedy).inOrder()
    }

    @Test
    fun `when top genre rank is zero should return unsorted search results`() = runTest {
        // Given
        val searchResults = listOf(seriesAction, seriesComedy)
        val genreWithZeroRank = Genre(id = 35, title = "Comedy", rank = 0)
        coEvery { seriesRepository.searchSeriesByName(any()) } returns searchResults
        coEvery { seriesRepository.getSeriesGenres() } returns listOf(genreWithZeroRank)

        // When
        val result = searchSeriesByNameUseCase("test")

        // Then
        assertThat(result).containsExactly(seriesAction, seriesComedy).inOrder()
    }

    @Test
    fun `when genres exist with rank should return series sorted by top genre`() = runTest {
        // Given
        val searchResults = listOf(seriesAction, seriesComedy)
        val favoriteGenre = Genre(id = 35, title = "Comedy", rank = 1)
        coEvery { seriesRepository.searchSeriesByName(any()) } returns searchResults
        coEvery { seriesRepository.getSeriesGenres() } returns listOf(favoriteGenre)

        // When
        val result = searchSeriesByNameUseCase("test")

        // Then
        assertThat(result).containsExactly(seriesComedy, seriesAction).inOrder()
    }
}