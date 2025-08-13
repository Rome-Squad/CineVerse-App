package com.giraffe.media.match.usecase

import com.giraffe.media.match.repository.MatchRepository
import com.giraffe.media.series.entity.Series
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMatchingSeriesUseCaseTest {
    private val repository: MatchRepository = mockk(relaxed = true)
    private val useCase = GetMatchingSeriesUseCase(repository)

    @Test
    fun `invoke should call getMatchingSeries with all parameters`() = runTest {
        val genreIds = "18,10765"
        val minRuntime = 30
        val maxRuntime = 60
        val earliestDate = "2015-01-01"
        val latestDate = "2024-01-01"

        val expectedSeries = listOf<Series>(mockk(relaxed = true), mockk(relaxed = true))
        coEvery {
            repository.getMatchingSeries(genreIds, minRuntime, maxRuntime, earliestDate, latestDate)
        } returns expectedSeries

        val result = useCase(genreIds, minRuntime, maxRuntime, earliestDate, latestDate)

        coVerify(exactly = 1) {
            repository.getMatchingSeries(genreIds, minRuntime, maxRuntime, earliestDate, latestDate)
        }

        assertEquals(expectedSeries, result)
    }


    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        val genreIds = "10759"

        coEvery {
            repository.getMatchingSeries(genreIds, null, null, null, null)
        } returns emptyList()

        val result = useCase(genreIds)

        assertEquals(emptyList<Series>(), result)
    }


}
