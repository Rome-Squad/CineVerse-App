package com.giraffe.media.series.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.repository.SeriesRepository
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
            Genre(
                id = 1, title = "Action",
                rank = 0
            ),
            Genre(
                id = 2, title = "Drama",
                rank = 0
            ),
            Genre(
                id = 3, title = "Fantasy",
                rank = 0
            ),
            Genre(
                id = 4, title = "Horror",
                rank = 0
            )
        )
        coEvery { seriesRepository.getSeriesGenres() } returns expectedGenres

        val result = getSeriesGenresUseCase()

        assertThat(result).isEqualTo(expectedGenres)
        assertThat(result).hasSize(4)
        coVerify(exactly = 1) { seriesRepository.getSeriesGenres() }
    }

}