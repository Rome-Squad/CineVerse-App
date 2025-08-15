package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetSeriesByGenresUseCaseTest {
    private val repository: SeriesRepository = mockk(relaxed = true)
    private val useCase: GetSeriesByGenresUseCase = GetSeriesByGenresUseCase(repository)


    @Test
    fun `should return list of series from repository`() = runTest {
        val genreId = 1
        val page = 5
        val expectedSeries = listOf(
            createFakeSeries(
                id = 1,
                name = "Series 1"
            ),
            createFakeSeries(
                id = 2,
                name = "Series 2"
            )
        )
        coEvery { repository.getByGenreId(genreId, page) } returns expectedSeries

        val result = useCase(genreId, page)

        assertThat(result).isEqualTo(expectedSeries)
    }
}