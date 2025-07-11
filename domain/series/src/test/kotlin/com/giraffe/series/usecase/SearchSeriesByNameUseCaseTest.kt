package com.giraffe.series.usecase

import com.giraffe.series.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchSeriesByNameUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var searchSeriesByNameUseCase: SearchSeriesByNameUseCase


    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        searchSeriesByNameUseCase = SearchSeriesByNameUseCase(seriesRepository)
    }

    @Test
    fun `Should return list of series When repository returns success`() = runTest {
        // Given
        val seriesName = "Batman"
        val expectedSeries = listOf(
            fakeSeries(id = 1, name = "Batman 1"),
            fakeSeries(id = 2, name = "Batman 3"),
            fakeSeries(id = 3, name = "Batman 2")
        )
        coEvery { seriesRepository.searchSeriesByName(seriesName) } returns expectedSeries

        // When
        val result = searchSeriesByNameUseCase(seriesName)

        // Then
        assertEquals(expectedSeries, result)
        assertEquals(3, result.size)
        coVerify(exactly = 1) { seriesRepository.searchSeriesByName(seriesName) }
    }

}