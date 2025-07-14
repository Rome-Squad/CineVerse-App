package com.giraffe.series.usecase

import com.giraffe.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSeriesDetailsUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var getSeriesDetailsUseCase: GetSeriesDetailsUseCase


    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        getSeriesDetailsUseCase = GetSeriesDetailsUseCase(seriesRepository)
    }

    @Test
    fun `Should return details of series When repository returns success`() = runTest {
        // Given
        val seriesId = 1
        val expectedResult = fakeSeriesDetails(id = 1, name = "batman")
        coEvery { seriesRepository.getSeriesDetails(seriesId) } returns expectedResult

        // When
        val result = getSeriesDetailsUseCase(seriesId)

        // Then
        assertThat(expectedResult).isEqualTo(result)
        coVerify(exactly = 1) { seriesRepository.getSeriesDetails(seriesId) }
    }


}