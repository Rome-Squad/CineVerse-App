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
    private val sampleGenres = listOf(
        SeriesGenreCacheDto(id = 1, name = "Action", count = 1)
    )


    @Test
    fun `getGenres returns genres if cache is valid`() = runTest {
        coEvery { seriesDao.getGenres() } returns sampleGenres

        val result = dataSource.getGenres()

        assertThat(result).isEqualTo(sampleGenres)
    }

    @Test
    fun `getGenres returns empty if cache expired`() = runTest {
        coEvery { seriesDao.getGenres() } returns emptyList()

        val result = dataSource.getGenres()

        assertThat(result).isEmpty()
    }


    @Test
    fun `insertGenres inserts genres`() = runTest {
        dataSource.insertGenres(sampleGenres)

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
        coEvery { seriesDao.getGenresByIds(genreIds) } returns sampleGenres

        val result = dataSource.getGenresByIDs(genreIds)

        assertThat(result).isEqualTo(sampleGenres)
    }

    @Test
    fun `clearGenres clears DAO`() = runTest {
        dataSource.clearGenres()

        coVerify { seriesDao.clearGenres() }
    }

    @Test
    fun `clearSeries clears DAO`() = runTest {
        dataSource.clearSeries()

        coVerify {
            seriesDao.clearSeriesExceptRecentViewed()
            seriesDao.clearPopularSeriesTable()
            seriesDao.clearRecentlyReleasedSeriesTable()
            seriesDao.clearTopRatedSeriesTable()
            seriesDao.clearMatchesYourVibeSeriesTable()
        }
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
        coEvery { seriesDao.getPopularitySeries(10) } returns sampleSeries

        val result = dataSource.getPopularitySeries(10)

        assertThat(result).isEqualTo(sampleSeries)
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
        coEvery { seriesDao.getRecentlyReleasedSeries(10) } returns sampleSeries

        val result = dataSource.getRecentlyReleasedSeries(10)

        assertThat(result).isEqualTo(sampleSeries)
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
        coEvery { seriesDao.getTopRatedSeries(10) } returns sampleSeries

        val result = dataSource.getTopRatedSeries(10)

        assertThat(result).isEqualTo(sampleSeries)
    }

    @Test
    fun `deleteSeriesById should delete series by ID`() = runTest {
        val seriesId = 1
        dataSource.deleteSeriesById(seriesId)

        coVerify { seriesDao.deleteSeriesById(seriesId) }
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
        coEvery { seriesDao.getMatchesYourVibeSeries(10) } returns sampleSeries

        val result = dataSource.getMatchesYourVibe(10)

        assertThat(result).isEqualTo(sampleSeries)
    }

    @Test
    fun `getRecentSeries should sync time before returning recent series`() = runTest {
        every { seriesDao.getRecentSeries() } returns flowOf(sampleSeries)
        coEvery { seriesDao.syncRecentViewedTime() } just Runs

        val result = mutableListOf<List<SeriesCacheDto>>()

        dataSource.getRecentSeries().collect { result.add(it) }

        assertThat(result).containsExactly(sampleSeries)
    }

    @Test
    fun `clearRecentSeries clears DAO recent table`() = runTest {
        dataSource.clearRecentSeries()

        coVerify { seriesDao.clearRecentSeries() }
    }
}
