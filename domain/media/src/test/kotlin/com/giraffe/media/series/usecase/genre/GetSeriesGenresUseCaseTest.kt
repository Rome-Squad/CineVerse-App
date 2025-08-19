package com.giraffe.media.series.usecase.genre

import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.giraffe.media.util.fakeGenres

class GetSeriesGenresUseCaseTest {

    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private val useCase: ObserveSeriesGenresUseCase =
        ObserveSeriesGenresUseCase(seriesRepository)


    @Test
    fun `Should return list of genres When repository returns success`() = runTest {
        val expectedGenres = flowOf(fakeGenres)
        coEvery { seriesRepository.observeGenres() } returns expectedGenres

        val result = useCase()

        assertThat(result).isEqualTo(expectedGenres)
    }
}