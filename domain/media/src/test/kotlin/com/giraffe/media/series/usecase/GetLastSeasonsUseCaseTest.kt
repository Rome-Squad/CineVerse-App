package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetLastSeasonsUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var getLastSeasonsUseCase: GetLastSeasonsUseCase


    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        getLastSeasonsUseCase = GetLastSeasonsUseCase(seriesRepository)
    }

    @Test
    fun `Should return list of season When repository returns success`() = runTest {
        val seriesId = 1
        val expectedResult = listOf(
            fakeSeason(id = 1, name = "season 1"),
            fakeSeason(id = 2, name = "season 2"),
            fakeSeason(id = 3, name = "season 3")
        )
        coEvery { seriesRepository.getSeasonOfSeries(seriesId) } returns expectedResult

        val result = getLastSeasonsUseCase(seriesId)

        assertThat(expectedResult).isEqualTo(result)
        coVerify(exactly = 1) { seriesRepository.getSeasonOfSeries(seriesId) }
    }
}