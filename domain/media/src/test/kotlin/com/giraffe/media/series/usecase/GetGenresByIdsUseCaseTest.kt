package com.giraffe.media.series.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetGenresByIdsUseCaseTest {
    private lateinit var repository: SeriesRepository
    private lateinit var useCase: GetSeriesGenresByIdsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetSeriesGenresByIdsUseCase(repository)
    }

    @Test
    fun `should return list of series genres from repository`() = runTest {
        val seriesGenresIds = listOf(1, 2)
        val expectedGenres = listOf(
            Genre(
                id = 1, title = "Action",
                rank = 0
            ),
            Genre(
                id = 2, title = "Comedy",
                rank = 0
            )
        )
        coEvery { repository.getSeriesGenresByIds(seriesGenresIds) } returns expectedGenres

        val result = useCase(seriesGenresIds)

        assertThat(result).isEqualTo(expectedGenres)
    }
}