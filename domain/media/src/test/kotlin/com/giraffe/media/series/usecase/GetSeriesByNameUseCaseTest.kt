package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.util.fakeTopGenre
import com.giraffe.media.util.genreWithZeroRank
import com.giraffe.media.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetSeriesByNameUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk()
    private val searchSeriesByNameUseCase: GetSeriesByNameUseCase =
        GetSeriesByNameUseCase(seriesRepository)

    private val seriesAction =
        createFakeSeries(id = 1, name = "Action Series", genreIDs = listOf(fakeTopGenre.id))
    private val seriesComedy =
        createFakeSeries(id = 2, name = "Comedy Series", genreIDs = listOf(genreWithZeroRank.id))
    private val name = "test"


    @Test
    fun `when top genre rank is zero should return unsorted search results`() = runTest {
        val searchResults = listOf(seriesAction, seriesComedy)
        coEvery { seriesRepository.getByName(any(), any()) } returns searchResults
        coEvery { seriesRepository.getTopGenreCount() } returns fakeTopGenre

        val result = searchSeriesByNameUseCase(name, page)

        assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun `when genres exist with rank should return series sorted by top genre`() = runTest {
        val searchResults = listOf(seriesComedy, seriesAction)
        coEvery { seriesRepository.getByName(name, page) } returns searchResults
        coEvery { seriesRepository.getTopGenreCount() } returns fakeTopGenre

        val result = searchSeriesByNameUseCase(name, page)

        assertThat(result).containsExactly(seriesAction, seriesComedy).inOrder()
    }
}