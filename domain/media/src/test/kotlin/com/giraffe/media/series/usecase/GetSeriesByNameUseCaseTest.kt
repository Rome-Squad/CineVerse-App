package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.entity.Genre
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetSeriesByNameUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val searchSeriesByNameUseCase: GetSeriesByNameUseCase =
        GetSeriesByNameUseCase(seriesRepository)

    private val seriesAction =
        createFakeSeries(id = 1, name = "Action Series", genreIDs = listOf(10759))
    private val seriesComedy =
        createFakeSeries(id = 2, name = "Comedy Series", genreIDs = listOf(35))


    @Test
    fun `when top genre rank is zero should return unsorted search results`() = runTest {
        val searchResults = listOf(seriesAction, seriesComedy)
        val genreWithZeroRank = Genre(id = 35, title = "Comedy", rank = 0)
        coEvery { seriesRepository.getByName(any(), any()) } returns searchResults
        coEvery { seriesRepository.getGenres() } returns listOf(genreWithZeroRank)

        val result = searchSeriesByNameUseCase("test", 1)

        assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun `when genres exist with rank should return series sorted by top genre`() = runTest {
        val searchResults = listOf(seriesAction, seriesComedy)
        val favoriteGenre = Genre(id = 35, title = "Comedy", rank = 1)
        coEvery { seriesRepository.getByName(any(), any()) } returns searchResults
        coEvery { seriesRepository.getGenres() } returns listOf(favoriteGenre)

        val result = searchSeriesByNameUseCase("test", 1)

        assertThat(result).containsExactly(seriesComedy, seriesAction).inOrder()
    }
}