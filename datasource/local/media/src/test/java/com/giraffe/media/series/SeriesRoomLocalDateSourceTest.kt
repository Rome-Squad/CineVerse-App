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
            id = 1,
            name = "Vikings",
            overview = "desc",
            rate = 8.0f,
            posterUrl = "poster",
            backdropUrl = "backdrop",
            genresID = listOf(1),
            releaseYear = "2015",
            youtubeVideoId = "youtube"
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

        val result = dataSource.getGenres()

        assertThat(result).isEqualTo(sampleGenres)
    }

    @Test
    fun `getCachedGenres returns empty if cache expired`() = runTest {
        coEvery { dao.getAllGenres() } returns emptyList()

        val result = dataSource.getGenres()

        assertThat(result).isEmpty()
    }


    @Test
    fun `insertGenres inserts genres`() = runTest {
        val newGenres = listOf(
            SeriesGenreCacheDto(id = 2, name = "Drama", count = 2)
        )

        dataSource.insertGenres(newGenres)

        coVerify { dao.upsertGenres(newGenres) }
    }

    @Test
    fun `clearAllSeriesExceptRecentlyViewed clears DAO`() = runTest {
        dataSource.clearAllSeriesExceptRecentlyViewed()

        coVerify { dao.clearAllSeriesExceptRecentlyViewed() }
    }

    @Test
    fun `clearAllSeries clears DAO`() = runTest {
        dataSource.clearAllSeries()

        coVerify { dao.clearAllSeries() }
    }

    @Test
    fun `clearAllGenres clears DAO`() = runTest {
        dataSource.clearAllGenres()

        coVerify { dao.clearAllGenres() }
    }

    @Test
    fun `storeRecentSeries marks as viewed`() = runTest {
        val seriesId = 1
        dataSource.insertRecentSeries(seriesId)

        coVerify { dao.markSeriesAsViewed(seriesId, any()) }
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

    @Test
    fun `incrementInteractionCountForGenres calls DAO`() = runTest {
        val genreIds = listOf(1, 2, 3)

        dataSource.incrementInteractionCountForGenres(genreIds)

        coVerify { dao.incrementInteractionCountForGenres(genreIds) }
    }

    @Test
    fun `getGenresByIDs returns genres from DAO`() = runTest {
        val genreIds = listOf(1)
        coEvery { dao.getGenresByIds(genreIds) } returns sampleGenres

        val result = dataSource.getGenresByIDs(genreIds)

        assertThat(result).isEqualTo(sampleGenres)
    }

    @Test
    fun `insertPopularitySeries performs upsert with popularity flag`() = runTest {
        dataSource.insertPopularitySeries(sampleSeries)

        coVerify {
            dao.upsertSeries(sampleSeries)
            dao.upsertPopularSeriesIDs(sampleSeries.map { it.toPopularSeriesCacheDto() })
        }
    }

    @Test
    fun `getPopularitySeries returns series`() = runTest {
        coEvery { dao.getPopularitySeries(10) } returns sampleSeries

        val result = dataSource.getPopularitySeries(10)

        assertThat(result).isEqualTo(sampleSeries)
    }

    @Test
    fun `insertRecentlyReleasedSeries performs upsert with recentlyReleased flag`() = runTest {
        dataSource.insertRecentlyReleasedSeries(sampleSeries)

        coVerify {
            dao.upsertSeries(sampleSeries)
            dao.upsertRecentlyReleasedSeriesIDs(sampleSeries.map { it.toRecentlyReleasedSeriesCacheDto() })
        }
    }

    @Test
    fun `getRecentlyReleasedSeries returns series`() = runTest {
        coEvery { dao.getRecentlyReleasedSeries(10) } returns sampleSeries

        val result = dataSource.getRecentlyReleasedSeries(10)

        assertThat(result).isEqualTo(sampleSeries)
    }

    @Test
    fun `insertTopRatedSeries performs upsert with topRated flag`() = runTest {
        dataSource.insertTopRatedSeries(sampleSeries)

        coVerify {
            dao.upsertSeries(sampleSeries)
            dao.upsertTopRatedSeriesIDs(sampleSeries.map { it.toTopRatedSeriesCacheDto() })
        }
    }

    @Test
    fun `getTopRatedSeries returns series`() = runTest {
        coEvery { dao.getTopRatedSeries(10) } returns sampleSeries

        val result = dataSource.getTopRatedSeries(10)

        assertThat(result).isEqualTo(sampleSeries)
    }
}
