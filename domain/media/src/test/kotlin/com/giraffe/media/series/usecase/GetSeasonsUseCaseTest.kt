package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeason
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetSeasonsUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val getLastSeasonsUseCase: GetSeasonsUseCase = GetSeasonsUseCase(seriesRepository)


    @Test
    fun `Should return list of season When repository returns success`() = runTest {
        val seriesId = 1
        val expectedResult = listOf(
            createFakeSeason(id = 1),
            createFakeSeason(id = 2),
            createFakeSeason(id = 3)
        )
        coEvery { seriesRepository.getSeasons(seriesId) } returns expectedResult

        val result = getLastSeasonsUseCase(seriesId)

        assertThat(expectedResult).isEqualTo(result)
    }
}