package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SeriesRoomLocalDateSourceTest {

    private lateinit var dao: SeriesDao
    private lateinit var dataSource: SeriesRoomLocalDateSource

    private val sampleSeries = listOf(
        SeriesCacheDto(
            1,
            "Vikings",
            "desc",
            8.0f,
            "poster",
            "backdrop",
            listOf(1),
            "2015"
        )
    )
    private val sampleGenres = listOf(
        SeriesGenreCacheDto(id = 1, name = "Action", count = 1)
    )

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        dataSource = SeriesRoomLocalDateSource(dao)
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
    fun `saveGenres inserts genres`() = runTest {
        val genres = listOf(SeriesGenreCacheDto(id = 2, name = "Drama", count = 2))

        dataSource.insertGenres(genres)

        coVerify { dao.insertGenres(genres) }
    }

    @Test
    fun `clearAllData clears DAO`() = runTest {
        dataSource.clearAllData()

        coVerify { dao.clearAllSeries() }
        coVerify { dao.clearAllGenres() }
    }

    @Test
    fun `storeRecentSeries marks as viewed`() = runTest {
        dataSource.insertRecentSeries(1)

        coVerify { dao.markSeriesAsViewed(1) }
    }

    @Test
    fun `clearRecentSeries clears DAO recent table`() = runTest {
        dataSource.clearRecentSeries()

        coVerify { dao.clearRecentSeries() }
    }

    @Test
    fun `getRecentSeries returns series marked as recent`() = runTest {
        coEvery { dao.getRecentSeries() } returns flowOf(sampleSeries)

        val result = dataSource.getRecentSeries().first()

        coVerify { dao.getRecentSeries() }
        assertThat(result).isEqualTo(sampleSeries)
    }

}
