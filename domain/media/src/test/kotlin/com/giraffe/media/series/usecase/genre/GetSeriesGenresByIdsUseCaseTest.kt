package com.giraffe.media.series.usecase.genre

import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.giraffe.media.util.fakeGenres

class GetSeriesGenresByIdsUseCaseTest {
    private val repository: SeriesRepository = mockk(relaxed = true)
    private val useCase: GetSeriesGenresByIdsUseCase = GetSeriesGenresByIdsUseCase(repository)


    @Test
    fun `should return list of series genres from repository`() = runTest {
        val seriesGenresIds = listOf(1, 2)
        val expectedGenres = flowOf(fakeGenres)
        coEvery { repository.getGenresByIds(seriesGenresIds) } returns expectedGenres

        val result = useCase(seriesGenresIds)

        assertThat(result).isEqualTo(expectedGenres.first())
    }
}