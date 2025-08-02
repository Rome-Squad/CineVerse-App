package com.giraffe.media.series

import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.datasource.local.cacheDto.SeasonCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.remote.dto.GenreDto
import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SeriesRepositoryImplTest {
    private lateinit var local: SeriesLocalDateSource
    private lateinit var localExplore: LocalExploreDataSource
    private lateinit var remote: SeriesRemoteDataSource
    private lateinit var repository: SeriesRepository
    private val remoteSeriesDto = listOf(
        SeriesDto(
            id = 1,
            name = "Vikings",
            voteCount = 1000,
            overview = "desc",
            popularity = 90.0,
            originalName = "Vikings",
            firstAirDate = "2015-01-01",
            posterPath = "poster",
            backdropPath = "backdrop",
            voteAverage = 8.0,
            genreIds = listOf(1),
            originCountry = listOf("US"),
            originalLanguage = "en"
        )
    )

    private val sampleSeries = listOf(
        Series(
            id = 1,
            name = "Vikings",
            overview = "desc",
            rating = 8.0f,
            posterUrl = "poster",
            backdropUrl = "backdrop",
            genreIDs = listOf(1),
            releaseYear = "2015"
        )
    )

    private val remoteGenres = listOf(
        GenreDto(id = 1, name = "Action")
    )

    private val cachedSeries = listOf(
        SeriesCacheDto(
            id = 1,
            name = "Vikings",
            overview = "desc",
            rate = 8.0f,
            posterUrl = "poster",
            backdropUrl = "backdrop",
            genresID = listOf(1),
            releaseYear = "2015"
        )
    )

    private val cachedSeasons = listOf(
        SeasonCacheDto(1,
            1,
            "S1",
            "desc",
            8.0f,
            "poster",
            1,
            "2015",
            10)
    )

    private val cachedGenres = listOf(
        SeriesGenreCacheDto(1, "Action", 0)
    )

    @Before
    fun setup() {
        local = mockk(relaxed = true)
        remote = mockk(relaxed = true)
        repository = SeriesRepositoryImpl(remote, local, localExplore)
    }

    @Test
    fun `searchSeriesByName returns cached result if available`() = runTest {
        coEvery { local.getCachedSeriesForName("vikings", 1) } returns cachedSeries
        coEvery { local.getSeasonsForSeries(1) } returns cachedSeasons

        val result = repository.searchSeriesByName("vikings", 1)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Vikings")
        coVerify(exactly = 0) { remote.getSeriesByName(any()) }
    }

    @Test
    fun `searchSeriesByName fetches remote if cache empty and saves`() = runTest {
        coEvery { local.getCachedSeriesForName("vikings", 1) } returns emptyList()
        coEvery { remote.getSeriesByName("vikings") } returns remoteSeriesDto

        val result = repository.searchSeriesByName("vikings", 1)

        assertThat(result.first().name).isEqualTo("Vikings")
        coVerify { remote.getSeriesByName("vikings") }
        coVerify {
            local.saveSearchResult(
                seriesList = match { it.first().id == 1 }
            )
        }
    }

    @Test
    fun `getSeriesGenres returns cached if valid`() = runTest {
        coEvery { local.getCachedGenres() } returns cachedGenres

        val result = repository.getSeriesGenres()

        assertThat(result).hasSize(1)
        assertThat(result.first().title).isEqualTo("Action")
        coVerify(exactly = 0) { remote.getGenres() }
    }

    @Test
    fun `getSeriesGenres fetches from remote if cache empty and saves`() = runTest {
        coEvery { local.getCachedGenres() } returns emptyList()
        coEvery { remote.getGenres() } returns remoteGenres

        val result = repository.getSeriesGenres()

        assertThat(result).hasSize(1)
        assertThat(result.first().title).isEqualTo("Action")
        coVerify { local.saveGenres(match { it.first().id == 1 }) }
    }

    @Test
    fun `getRecentSeries returns correct entities`() = runTest {
        coEvery { local.getRecentSeries() } returns flowOf(cachedSeries)
        coEvery { local.getSeasonsForSeries(1) } returns cachedSeasons

        val result = repository.getRecentSeries().first()

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Vikings")
        coVerify { local.getSeasonsForSeries(1) }
    }

    @Test
    fun `storeRecentSeries should call local storage`() = runTest {
        repository.addRecentSeries(sampleSeries[0])
        coVerify { local.storeRecentSeries(1) }
    }

    @Test
    fun `clearRecentSeries should call local clear`() = runTest {
        repository.clearRecentSeries()
        coVerify { local.clearRecentSeries() }
    }

    @Test
    fun `SeriesRepository should return Series`() = runTest {
        repository.getRecommendedSeries(1, 1)
        coVerify { remote.getSeriesRecommendations(1, 1) }
    }
}
