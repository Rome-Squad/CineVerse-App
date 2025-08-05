package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetTopRatedSeriesUseCaseTest {

    private lateinit var repository: SeriesRepository
    private lateinit var useCase: GetTopRatedSeriesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetTopRatedSeriesUseCase(repository)
    }

    @Test
    fun `given top rated series, when invoke is called, then return series list`() = runTest {
        val expectedSeries = listOf(
            Series(
                id = 101,
                name = "Breaking Bad",
                overview = "A high school chemistry teacher turned methamphetamine producer...",
                rating = 9.5f,
                posterUrl = "https://example.com/breakingbad.jpg",
                backdropUrl = "https://example.com/breakingbad.jpg",
                genreIDs = listOf(18, 80),
                releaseYear = "2008",
                seasons = listOf(
                    Season(
                        id = 1,
                        overview = "Season 1 overview",
                        rating = 9.0f,
                        posterUrl = "https://example.com/season1.jpg",
                        seasonNumber = 1,
                        releaseYear = "2008",
                        episodeCount = 7
                    )
                )
            )
        )
        val page = 1
        val limit = 10

        coEvery { repository.getTopRatedSeries(page = page, limit = limit) } returns expectedSeries

        val actualSeries = useCase(page = page, limit = limit)

        assertThat(actualSeries).isEqualTo(expectedSeries)
    }
}
