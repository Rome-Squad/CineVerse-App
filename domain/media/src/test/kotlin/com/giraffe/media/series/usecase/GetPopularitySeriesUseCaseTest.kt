package com.giraffe.media.series.usecase


import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPopularitySeriesUseCaseTest {

    private lateinit var repository: SeriesRepository
    private lateinit var useCase: GetPopularitySeriesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetPopularitySeriesUseCase(repository)
    }

    @Test
    fun `given popular series, when invoke is called, then return series list`() = runTest {
        // Given
        val expectedSeries = listOf(
            Series(
                id = 1,
                name = "Breaking Bad",
                overview = "High school teacher turns to crime",
                rating = 9.5f,
                posterUrl = "https://example.com/breakingbad.jpg",
                backdropUrl = "https://example.com/breakingbad.jpg",
                genreIDs = listOf(1, 2),
                releaseYear = "2008",
                seasons = listOf(
                    Season(
                        id = 101,
                        overview = "The beginning of it all",
                        rating = 9.0f,
                        posterUrl = "https://example.com/s1.jpg",
                        seasonNumber = 1,
                        releaseYear = "2008",
                        episodeCount = 7
                    )
                )
            )
        )

        coEvery { repository.getPopularitySeries(1) } returns expectedSeries

        // When
        val actualSeries = useCase(1)

        // Then
        coVerify(exactly = 1) { repository.getPopularitySeries(1) }
        assertThat(actualSeries).isEqualTo(expectedSeries)
    }
}
