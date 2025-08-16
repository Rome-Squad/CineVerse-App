package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetSeriesDetailsUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase =
        GetSeriesDetailsUseCase(seriesRepository)


    @Test
    fun `Should return details of series reviews When repository returns success`() = runTest {
        val seriesId = 1
        val expectedResult = createFakeSeries(id = 1, name = "batman")
        coEvery { seriesRepository.getDetails(seriesId) } returns expectedResult

        val result = getSeriesDetailsUseCase(seriesId)

        assertThat(expectedResult).isEqualTo(result)
    }
}