package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.series.util.createFakeSeries
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetFilteredSeriesUseCaseTest {
    private val seriesRepository = mockk<SeriesRepository>()
    private val getFilteredSeriesUseCase = GetFilteredSeriesUseCase(seriesRepository)

    @Test
    fun `invoke should call repository to return filtered series matches keyword`() = runTest {
        val keyword = "LEGO Ninja"
        val series = createFakeSeries(name = "LEGO Ninjago Legends: Monstrosity")
        val expectedResult = listOf(series)
        coEvery {
            seriesRepository.discoverSeries(
                keywords = keyword,
                page = 1
            )
        } returns expectedResult

        val result = getFilteredSeriesUseCase(keywords = keyword, page = 1)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should call repository to return filtered series matches genreIds`() = runTest {
        val genreIds = listOf(1, 2, 3)
        val series = createFakeSeries(genreIDs = genreIds)
        val expectedResult = listOf(series)
        coEvery {
            seriesRepository.discoverSeries(
                genreId = genreIds,
                page = 1
            )
        } returns expectedResult

        val result = getFilteredSeriesUseCase(genreId = genreIds, page = 1)

        assertThat(result).isEqualTo(expectedResult)
    }
}