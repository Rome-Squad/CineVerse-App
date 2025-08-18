package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.mapper.toMatchesYourVibeSeriesCacheDto
import com.giraffe.media.series.mapper.toPopularSeriesCacheDto
import com.giraffe.media.series.mapper.toRecentlyReleasedSeriesCacheDto
import com.giraffe.media.series.mapper.toTopRatedSeriesCacheDto
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SeriesLocalDataSourceImpTest {

    private val seriesDao: SeriesDao = mockk(relaxed = true)
    private val dataSource: SeriesLocalDataSourceImp = SeriesLocalDataSourceImp(seriesDao)

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
    private val expectedSeriesFlow = flowOf(sampleSeries)
    private val sampleGenres = listOf(
        SeriesGenreCacheDto(id = 1, name = "Action", count = 1)
    )


    @Test
    fun `getGenres returns genres if cache is valid`() = runTest {
        val expectedGenres = flowOf(sampleGenres)
        coEvery { seriesDao.getGenres() } returns expectedGenres

        val result = dataSource.getGenres()

        assertThat(result).isEqualTo(expectedGenres)
    }

    @Test
    fun `getGenres returns empty if cache expired`() = runTest {
        coEvery { seriesDao.getGenres() } returns flowOf(emptyList())

        val result = dataSource.getGenres().first()

        assertThat(result).isEmpty()
    }


    @Test
    fun `insertGenres inserts genres`() = runTest {
        dataSource.syncGenres(sampleGenres)

        coVerify { seriesDao.upsertGenres(sampleGenres) }
    }

    @Test
    fun `incrementInteractionCountForGenres calls DAO`() = runTest {
        val genreIds = listOf(1, 2, 3)

        dataSource.incrementInteractionCountForGenres(genreIds)

        coVerify { seriesDao.incrementInteractionCountForGenres(genreIds) }
    }

    @Test
    fun `getGenresByIDs returns genres from DAO`() = runTest {
        val genreIds = listOf(1)
        val expectedGenres = flowOf(sampleGenres)
        coEvery { seriesDao.getGenresByIds(genreIds) } returns expectedGenres

        val result = dataSource.getGenresByIDs(genreIds)

        assertThat(result).isEqualTo(expectedGenres)
    }

    @Test
    fun `clearGenres clears DAO`() = runTest {
        dataSource.clearGenres()

        coVerify { seriesDao.clearGenres() }
    }

    @Test
    fun `insertPopularitySeries should upsert series and popularSeriesIDs`() = runTest {
        dataSource.insertPopularitySeries(sampleSeries)

        coVerify {
            seriesDao.upsertSeries(sampleSeries)
            seriesDao.upsertPopularSeriesIDs(sampleSeries.map { it.toPopularSeriesCacheDto() })
        }
    }

    @Test
    fun `getPopularitySeries returns series`() = runTest {
        coEvery { seriesDao.getPopularitySeries(10) } returns expectedSeriesFlow

        val result = dataSource.getPopularitySeries(10)

        assertThat(result).isEqualTo(expectedSeriesFlow)
    }

    @Test
    fun `insertRecentlyReleasedSeries  should upsert series and RecentlyReleasedSeriesIDs`() =
        runTest {
            dataSource.insertRecentlyReleasedSeries(sampleSeries)

            coVerify {
                seriesDao.upsertSeries(sampleSeries)
                seriesDao.upsertRecentlyReleasedSeriesIDs(sampleSeries.map { it.toRecentlyReleasedSeriesCacheDto() })
            }
        }

    @Test
    fun `getRecentlyReleasedSeries returns series`() = runTest {
        coEvery { seriesDao.getRecentlyReleasedSeries(10) } returns expectedSeriesFlow

        val result = dataSource.getRecentlyReleasedSeries(10)

        assertThat(result).isEqualTo(expectedSeriesFlow)
    }

    @Test
    fun `insertTopRatedSeries  should upsert series and topRatedSeriesIDs`() = runTest {
        dataSource.insertTopRatedSeries(sampleSeries)

        coVerify {
            seriesDao.upsertSeries(sampleSeries)
            seriesDao.upsertTopRatedSeriesIDs(sampleSeries.map { it.toTopRatedSeriesCacheDto() })
        }
    }

    @Test
    fun `getTopRatedSeries returns series`() = runTest {
        coEvery { seriesDao.getTopRatedSeries(10) } returns expectedSeriesFlow

        val result = dataSource.getTopRatedSeries(10)

        assertThat(result).isEqualTo(expectedSeriesFlow)
    }

    @Test
    fun `deleteSeriesById should delete series by ID`() = runTest {
        val seriesId = 1
        dataSource.deleteSeriesFromHistoryById(seriesId)

        coVerify { seriesDao.deleteRecentlyViewedSeriesById(seriesId) }
    }

    @Test
    fun `getTopGenreCount returns top genre count`() = runTest {
        coEvery { seriesDao.getTopGenreCount() } returns sampleGenres.first()

        val result = dataSource.getTopGenreCount()

        assertThat(result).isEqualTo(sampleGenres.first())
    }

    @Test
    fun `insertMatchesYourVibe  should upsert series and matchesYourVibeSeriesIDs`() = runTest {
        dataSource.insertMatchesYourVibe(sampleSeries)

        coVerify {
            seriesDao.upsertSeries(sampleSeries)
            seriesDao.upsertMatchesYourVibeSeries(sampleSeries.map(SeriesCacheDto::toMatchesYourVibeSeriesCacheDto))
        }
    }

    @Test
    fun `getMatchesYourVibe returns series`() = runTest {
        coEvery { seriesDao.getMatchesYourVibeSeries(10) } returns expectedSeriesFlow

        val result = dataSource.getMatchesYourVibe(10)

        assertThat(result).isEqualTo(expectedSeriesFlow)
    }

    @Test
    fun `getRecentSeries should sync time before returning recent series`() = runTest {
        val page = 1
        val pageSize = 10
        every {
            seriesDao.getRecentlyViewedSeries(
                page = page,
                pageSize = pageSize
            )
        } returns flowOf(sampleSeries)
        coEvery { seriesDao.syncRecentViewedTime() } just Runs

        val result = mutableListOf<List<SeriesCacheDto>>()

        dataSource.getRecentSeries(page = page, pageSize = pageSize).collect { result.add(it) }

        assertThat(result).containsExactly(sampleSeries)
    }

    @Test
    fun `clearRecentSeries clears DAO recent table`() = runTest {
        dataSource.clearRecentSeries()

        coVerify { seriesDao.clearRecentlyViewedSeries() }
    }

    @Test
    fun `clearExceptRecentlyViewed clears DAO recent table`() = runTest {
        dataSource.clearExceptRecentlyViewed()

        coVerify(exactly = 1) {
            seriesDao.clearSeriesExceptRecentViewed()
            seriesDao.clearPopularSeriesTable()
            seriesDao.clearRecentlyReleasedSeriesTable()
            seriesDao.clearTopRatedSeriesTable()
            seriesDao.clearMatchesYourVibeSeriesTable()
        }
    }

    @Test
    fun `clearAll clears DAO recent table`() = runTest {
        dataSource.clearAll()

        coVerify(exactly = 1) {
            seriesDao.clearGenres()
            seriesDao.clearSeriesCache()
            seriesDao.clearPopularSeriesTable()
            seriesDao.clearRecentlyReleasedSeriesTable()
            seriesDao.clearTopRatedSeriesTable()
            seriesDao.clearMatchesYourVibeSeriesTable()
            seriesDao.clearRecentlyViewedSeries()
        }
    }
}
