package com.giraffe.media.series

import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.GenreDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.mapper.toEntity
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
            releaseYear = "2015-01-01",
            posterUrl = "poster",
            backdropUrl = "backdrop",
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
            releaseYear = null,
            popularity = null,
            youtubeVideoId = null,
            seasons = emptyList()
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
            releaseYear = null,
            popularity = 90.0
        )
    )

    private val cachedGenres = listOf(
        SeriesGenreCacheDto(1, "Action", 0)
    )

    private val seriesDetailsDto = SeriesDetailsDto(
        id = 1,
        name = "Vikings",
        voteCount = 1000,
        overview = "desc",
        originalName = "Vikings",
        releaseYear = "2015-01-01",
        posterUrl = "poster",
        backdropUrl = "backdrop",
        voteAverage = 8.0,
        originCountry = listOf("US"),
        originalLanguage = "en"
    )

    @Before
    fun setup() {
        local = mockk(relaxed = true)
        remote = mockk(relaxed = true)
        repository = SeriesRepositoryImpl(remote, local)
    }

    @Test
    fun `searchSeriesByName should return Series has correct name`() = runTest {
        coEvery { remote.getSeriesByName("Vikings", 1) } returns remoteSeriesDto

        val result = repository.getByName("Vikings", 1)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Vikings")
    }

    @Test
    fun `getSeriesGenres returns cached if valid`() = runTest {
        coEvery { local.getCachedGenres() } returns cachedGenres

        val result = repository.getGenres()

        assertThat(result).hasSize(1)
        assertThat(result.first().title).isEqualTo("Action")
        coVerify(exactly = 0) { remote.getGenres() }
    }

    @Test
    fun `getSeriesDetails should fetch details and trailer`() = runTest {
        val seriesId = 1
        val exceptedTrailer = "trailer123"
        coEvery { remote.getSeriesDetails(seriesId) } returns seriesDetailsDto
        coEvery { remote.getSeriesTrailerUrl(seriesId) } returns exceptedTrailer

        val result = repository.getDetails(seriesId)

        assertThat(result.name).isEqualTo("Vikings")
    }

    @Test
    fun `getRecentSeries should return mapped cached series`() = runTest {
        coEvery { local.getRecentSeries() } returns flowOf(cachedSeries)

        val result = repository.getRecentlyViewed().first()

        assertThat(result.first().name).isEqualTo("Vikings")
        coVerify { local.getRecentSeries() }
    }

    @Test
    fun `getSeriesGenres fetches from remote if cache empty and saves`() = runTest {
        coEvery { local.getCachedGenres() } returns emptyList()
        coEvery { remote.getGenres() } returns remoteGenres

        val result = repository.getGenres()

        assertThat(result).hasSize(1)
        assertThat(result.first().title).isEqualTo("Action")
        coVerify { local.insertGenres(match { it.first().id == 1 }) }
    }


    @Test
    fun `storeRecentSeries should call local storage`() = runTest {
        repository.addRecentlyViewed(sampleSeries[0])
        coVerify { local.insertRecentSeries(1) }
    }

    @Test
    fun `clearRecentSeries should call local clear`() = runTest {
        repository.clearRecentlyViewed()
        coVerify { local.clearRecentSeries() }
    }

    @Test
    fun `SeriesRepository should return Series`() = runTest {
        repository.getRecommended(1, 1, 10)
        coVerify { remote.getSeriesRecommendations(1, 1) }
    }

    @Test
    fun `getPopularitySeries returns cached if available`() = runTest {
        coEvery { local.getPopularitySeries(10) } returns cachedSeries

        val result = repository.getPopular(1, 10)

        assertThat(result.first().name).isEqualTo(cachedSeries.first().name)
        coVerify(exactly = 0) { remote.getPopularitySeries(any()) }
    }

    @Test
    fun `getPopularitySeries fetches from remote if cache is empty`() = runTest {
        coEvery { local.getPopularitySeries(10) } returns emptyList()
        coEvery { remote.getPopularitySeries(1) } returns remoteSeriesDto

        val result = repository.getPopular(1, 10)

        assertThat(result).isEqualTo(remoteSeriesDto.map(SeriesDto::toEntity))
        coVerify { local.insertPopularitySeries(any()) }
    }

    @Test
    fun `getRecentlyReleasedSeries returns cached if page is 1 and cache is not empty`() = runTest {
        coEvery { local.getRecentlyReleasedSeries(10) } returns cachedSeries

        val result = repository.getRecentlyReleased(1, 10)

        assertThat(result.first().name).isEqualTo(cachedSeries.first().name)
        coVerify(exactly = 0) { remote.getRecentlyReleasedSeries(any()) }
    }

    @Test
    fun `getRecentlyReleasedSeries fetches from remote if cache is empty`() = runTest {
        coEvery { local.getRecentlyReleasedSeries(10) } returns emptyList()
        coEvery { remote.getRecentlyReleasedSeries(1) } returns remoteSeriesDto

        val result = repository.getRecentlyReleased(1, 10)

        assertThat(result).isEqualTo(remoteSeriesDto.map(SeriesDto::toEntity))
        coVerify { local.insertRecentlyReleasedSeries(any()) }
    }

    @Test
    fun `getRecentlyReleasedSeries fetches from remote if page is greater than 1`() = runTest {
        coEvery { remote.getRecentlyReleasedSeries(2) } returns remoteSeriesDto

        val result = repository.getRecentlyReleased(2, 10)

        assertThat(result).isEqualTo(remoteSeriesDto.map(SeriesDto::toEntity))
        coVerify(exactly = 0) { local.getRecentlyReleasedSeries(any()) }
    }

    @Test
    fun `getTopRatedSeries returns cached if page is 1 and cache is not empty`() = runTest {
        coEvery { local.getTopRatedSeries(10) } returns cachedSeries

        val result = repository.getTopRated(1, 10)

        assertThat(result.first().name).isEqualTo(cachedSeries.first().name)
        coVerify(exactly = 0) { remote.getTopRatedSeries(any()) }
    }

    @Test
    fun `getTopRatedSeries fetches from remote if cache is empty`() = runTest {
        coEvery { local.getTopRatedSeries(10) } returns emptyList()
        coEvery { remote.getTopRatedSeries(1) } returns remoteSeriesDto

        val result = repository.getTopRated(1, 10)

        assertThat(result).isEqualTo(remoteSeriesDto.map(SeriesDto::toEntity))
        coVerify { local.insertTopRatedSeries(any()) }
    }

    @Test
    fun `getTopRatedSeries fetches from remote if page is greater than 1`() = runTest {
        coEvery { remote.getTopRatedSeries(2) } returns remoteSeriesDto

        val result = repository.getTopRated(2, 10)

        assertThat(result).isEqualTo(remoteSeriesDto.map(SeriesDto::toEntity))
        coVerify(exactly = 0) { local.getTopRatedSeries(any()) }
    }

    @Test
    fun `getRecommendedSeries returns cached if page is 1 and cache is not empty`() = runTest {
        coEvery { local.getRecommendedSeries(10) } returns cachedSeries

        val result = repository.getRecommended(1, 1, 10)

        assertThat(result.first().name).isEqualTo(cachedSeries.first().name)
        coVerify(exactly = 0) { remote.getSeriesRecommendations(any(), any()) }
    }

    @Test
    fun `getRecommendedSeries fetches from remote if cache is empty`() = runTest {
        val seriesId = 1
        coEvery { local.getRecommendedSeries(10) } returns emptyList()
        coEvery { remote.getSeriesRecommendations(seriesId, 1) } returns remoteSeriesDto

        val result = repository.getRecommended(seriesId, 1, 10)

        assertThat(result).isEqualTo(remoteSeriesDto.map(SeriesDto::toEntity))
        coVerify { local.insertRecommendedSeries(any()) }
    }

    @Test
    fun `getRecommendedSeries fetches from remote if page is greater than 1`() = runTest {
        val seriesId = 1
        coEvery { remote.getSeriesRecommendations(seriesId, 2) } returns remoteSeriesDto

        val result = repository.getRecommended(seriesId, 2, 10)

        assertThat(result).isEqualTo(remoteSeriesDto.map(SeriesDto::toEntity))
        coVerify(exactly = 0) { local.getRecommendedSeries(any()) }
    }


}
