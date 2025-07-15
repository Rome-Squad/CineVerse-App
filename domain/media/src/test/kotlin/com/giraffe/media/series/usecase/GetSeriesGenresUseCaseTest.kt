package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.SeriesGenre
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSeriesGenresUseCaseTest {

    private lateinit var seriesRepository: SeriesRepository
    private lateinit var getSeriesGenresUseCase: GetSeriesGenresUseCase

    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        getSeriesGenresUseCase = GetSeriesGenresUseCase(seriesRepository)
    }

    @Test
    fun `Should return list of genres When repository returns success`() = runTest {
        val expectedGenres = listOf(
            SeriesGenre(id = 1, name = "Action"),
            SeriesGenre(id = 2, name = "Drama"),
            SeriesGenre(id = 3, name = "Fantasy"),
            SeriesGenre(id = 4, name = "Horror")
        )
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = getSeriesGenresUseCase()

        assertThat(result).isEqualTo(expectedGenres)
        assertThat(result).hasSize(4)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

}