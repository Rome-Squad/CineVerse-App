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

class GetRecentlyReleasedSeriesUseCaseTest {

    private lateinit var repository: SeriesRepository
    private lateinit var useCase: GetRecentlyReleasedSeriesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetRecentlyReleasedSeriesUseCase(repository)
    }

    @Test
    fun `given recently released series, when invoke is called, then return series list`() = runTest {
        // Given
        val expectedSeries = listOf(
            Series(
                id = 2,
                name = "The Last of Us",
                overview = "Survival in a post-apocalyptic world",
                rating = 8.8f,
                posterUrl = "https://example.com/tlou.jpg",
                genreIDs = listOf(3, 6),
                releaseYear = "2023",
                seasons = listOf(
                    Season(
                        id = 201,
                        overview = "Joel and Ellie start their journey",
                        rating = 8.7f,
                        posterUrl = "https://example.com/s1tlou.jpg",
                        seasonNumber = 1,
                        releaseYear = "2023",
                        episodeCount = 9
                    )
                )
            )
        )

        coEvery { repository.getRecentlyReleasedSeries(1) } returns expectedSeries

        // When
        val actualSeries = useCase(1)

        // Then
        assertThat(actualSeries).isEqualTo(expectedSeries)
    }
}
