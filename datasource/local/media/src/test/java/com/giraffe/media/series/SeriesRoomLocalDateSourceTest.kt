package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.model.CachedSeasonDto
import com.giraffe.media.series.model.CachedSeriesGenreDto
import com.giraffe.media.series.model.SeriesCacheDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SeriesRoomLocalDateSourceTest {

    private lateinit var dao: SeriesDao
    private lateinit var dataSource: SeriesRoomLocalDateSource

    private val sampleSeries = listOf(
        SeriesCacheDto(1, "Vikings", "desc", 8.0f, "poster", listOf(1), "2015")
    )
    private val sampleSeasons = listOf(
        CachedSeasonDto(1, 1, "S1", "desc", 8.0f, "poster", 1, "2015", 10)
    )
    private val sampleGenres = listOf(
        CachedSeriesGenreDto(1, "Action")
    )

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        dataSource = SeriesRoomLocalDateSource(dao)
    }

    @Test
    fun `saveSearchResult inserts series only and preserves recent flag`() = runTest {
        coEvery { dao.getSeriesByIds(listOf(1)) } returns listOf(sampleSeries[0].copy(isRecent = true))

        dataSource.saveSearchResult(sampleSeries)

        coVerify {
            dao.insertSeries(match {
                it[0].id == 1 && it[0].isRecent
            })
        }
    }

    @Test
    fun `getCachedGenres returns genres if cache is valid`() = runTest {
        coEvery { dao.getAllGenres() } returns sampleGenres

        val result = dataSource.getCachedGenres()

        assertThat(result).isEqualTo(sampleGenres)
    }

    @Test
    fun `getCachedGenres returns empty if cache expired`() = runTest {
        coEvery { dao.getAllGenres() } returns emptyList()

        val result = dataSource.getCachedGenres()

        assertThat(result).isEmpty()
    }

    @Test
    fun `getCachedSeriesForName returns full data if cache valid`() = runTest {
        coEvery { dao.getSeriesByKeyword("vikings") } returns sampleSeries

        val result = dataSource.getCachedSeriesForName("vikings")

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Vikings")
    }

    @Test
    fun `getCachedSeriesForName returns empty if cache expired`() = runTest {
        coEvery { dao.getSeriesByKeyword("vikings") } returns emptyList()

        val result = dataSource.getCachedSeriesForName("vikings")

        assertThat(result).isEmpty()
    }

    @Test
    fun `saveGenres inserts genres`() = runTest {
        val genres = listOf(CachedSeriesGenreDto(2, "Drama"))

        dataSource.saveGenres(genres)

        coVerify { dao.insertGenres(genres) }
    }

    @Test
    fun `clearAllData clears DAO`() = runTest {
        dataSource.clearAllData()

        coVerify { dao.clearAllSeries() }
        coVerify { dao.clearAllSeasons() }
        coVerify { dao.clearAllGenres() }
    }

    @Test
    fun `storeRecentSeries marks as viewed`() = runTest {
        dataSource.storeRecentSeries(1)

        coVerify { dao.markSeriesAsViewed(1) }
    }

    @Test
    fun `clearRecentSeries clears DAO recent table`() = runTest {
        dataSource.clearRecentSeries()

        coVerify { dao.clearRecentSeries() }
    }

    @Test
    fun `getRecentSeries returns series marked as recent`() = runTest {
        coEvery { dao.getRecentSeries() } returns sampleSeries

        val result = dataSource.getRecentSeries()

        coVerify { dao.getRecentSeries() }
        assertThat(result).isEqualTo(sampleSeries)
    }

    @Test
    fun `getSeasonsForSeries returns seasons for series`() = runTest {
        coEvery { dao.getSeasonsForSeries(1) } returns sampleSeasons

        val result = dataSource.getSeasonsForSeries(1)

        coVerify { dao.getSeasonsForSeries(1) }
        assertThat(result).isEqualTo(sampleSeasons)
    }

    @Test
    fun `saveSearchResult does not insert when merged list is empty`() = runTest {
        coEvery { dao.getSeriesByIds(emptyList()) } returns emptyList()

        dataSource.saveSearchResult(emptyList())

        coVerify(exactly = 0) { dao.insertSeries(any()) }
    }

    @Test
    fun `saveSearchResult sets isRecent to false if series not in DB`() = runTest {
        val series = listOf(
            SeriesCacheDto(
                2,
                "Loki",
                "desc",
                9.0f,
                "poster",
                listOf(2),
                "2021",
                isRecent = true
            )
        )

        coEvery { dao.getSeriesByIds(listOf(2)) } returns emptyList()

        dataSource.saveSearchResult(series)

        coVerify {
            dao.insertSeries(match {
                it.size == 1 && it[0].id == 2 && !it[0].isRecent
            })
        }
    }

}
