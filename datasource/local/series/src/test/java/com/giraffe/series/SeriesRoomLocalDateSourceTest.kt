package com.giraffe.series

import com.giraffe.series.database.SearchCacheDao
import com.giraffe.series.database.SeriesDao
import com.giraffe.series.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.hours

@OptIn(ExperimentalCoroutinesApi::class)
class SeriesRoomLocalDataSourceTest {

 private lateinit var dao: SeriesDao
 private lateinit var cacheDao: SearchCacheDao
 private lateinit var dataSource: SeriesRoomLocalDateSource

 private val sampleSeries = listOf(
  CachedSeriesDto(1, "Vikings", "desc", 8.0f, "45m", "poster", listOf(1), "2015")
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
  cacheDao = mockk(relaxed = true)
  dataSource = SeriesRoomLocalDateSource(dao, cacheDao)
 }

 @Test
 fun `saveSearchResult should insert series, seasons, genres and cache`() = runTest {
  dataSource.saveSearchResult("vikings", sampleSeries, sampleSeasons, sampleGenres)

  coVerify { dao.insertSeries(sampleSeries) }
  coVerify { dao.insertSeasons(sampleSeasons) }
  coVerify { dao.insertGenres(sampleGenres) }
  coVerify {
   cacheDao.insertSearchCache(
    match<SearchCacheDto> {
     it.keyword == "vikings" && it.lastSearchedTime > 0
    }
   )
  }
 }

 @Test
 fun `saveSearchResult should skip empty lists`() = runTest {
  dataSource.saveSearchResult("vikings", emptyList(), emptyList(), emptyList())

  coVerify(exactly = 0) { dao.insertSeries(any()) }
  coVerify(exactly = 0) { dao.insertSeasons(any()) }
  coVerify(exactly = 0) { dao.insertGenres(any()) }
  coVerify {
   cacheDao.insertSearchCache(match { it.keyword == "vikings" })
  }
 }

 @Test
 fun `getCachedGenres should return empty if cache is invalid`() = runTest {
  val oldCache = SearchCacheDto("genres", 0L)
  coEvery { cacheDao.getCacheForKeyword("genres") } returns oldCache

  val result = dataSource.getCachedGenres()
  assertEquals(emptyList<CachedSeriesGenreDto>(), result)
  coVerify(exactly = 0) { dao.getAllGenres() }
 }

 @Test
 fun `getCachedGenres should return genres if cache is valid`() = runTest {
  val now = System.currentTimeMillis()
  val validCache = SearchCacheDto("genres", now)
  coEvery { cacheDao.getCacheForKeyword("genres") } returns validCache
  every { dao.getAllGenres() } returns flowOf(sampleGenres)

  val result = dataSource.getCachedGenres()
  assertEquals(sampleGenres, result)
 }

 @Test
 fun `getCachedSeriesForName should return null if no cache`() = runTest {
  coEvery { cacheDao.getCacheForKeyword("vikings") } returns null

  val result = dataSource.getCachedSeriesForName("vikings")
  assertNull(result)
 }

 @Test
 fun `getCachedSeriesForName should return null if cache is expired`() = runTest {
  val expiredTime = System.currentTimeMillis() - 2.hours.inWholeMilliseconds
  val cache = SearchCacheDto("vikings", expiredTime)
  coEvery { cacheDao.getCacheForKeyword("vikings") } returns cache

  val result = dataSource.getCachedSeriesForName("vikings")
  assertNull(result)
  coVerify { cacheDao.deleteCacheForKeyword("vikings") }
 }

 @Test
 fun `getCachedSeriesForName should return full data if valid`() = runTest {
  val now = System.currentTimeMillis()
  val cache = SearchCacheDto("vikings", now)

  coEvery { cacheDao.getCacheForKeyword("vikings") } returns cache
  coEvery { dao.getSeriesByKeyword("vikings") } returns sampleSeries
  every { dao.getSeasonsForSeries(1) } returns flowOf(sampleSeasons)
  every { dao.getAllGenres() } returns flowOf(sampleGenres)

  val result = dataSource.getCachedSeriesForName("vikings")

  assertEquals(1, result?.size)
  assertEquals("Vikings", result?.first()?.series?.name)
  assertEquals(sampleSeasons, result?.first()?.seasons)
  assertEquals(sampleGenres, result?.first()?.genres)
 }

 @Test
 fun `getCachedSeriesByGenre should return matched series`() = runTest {
  val genreId = 1
  coEvery { dao.getAllSeries() } returns flowOf(sampleSeries)
  every { dao.getSeasonsForSeries(1) } returns flowOf(sampleSeasons)
  every { dao.getAllGenres() } returns flowOf(sampleGenres)

  val result = dataSource.getCachedSeriesByGenre(genreId)

  assertEquals(1, result.size)
  assertEquals("Vikings", result.first().series.name)
 }

 @Test
 fun `getCachedSeriesByGenre should return empty if no matches`() = runTest {
  coEvery { dao.getAllSeries() } returns flowOf(emptyList())

  val result = dataSource.getCachedSeriesByGenre(99)
  assertTrue(result.isEmpty())
 }

 @Test
 fun `saveGenres should insert genres and cache them`() = runTest {
  val genres = listOf(
   CachedSeriesGenreDto(1, "Action"),
   CachedSeriesGenreDto(2, "Drama")
  )

  dataSource.saveGenres(genres)

  coVerify { dao.insertGenres(genres) }

  coVerify {
   cacheDao.insertSearchCache(
    match<SearchCacheDto> {
     it.keyword == "genres" && it.lastSearchedTime > 0
    }
   )
  }
 }

 @Test
 fun `getCachedGenres should return empty when cache is null`() = runTest {
  coEvery { cacheDao.getCacheForKeyword("genres") } returns null

  val result = dataSource.getCachedGenres()

  assertTrue(result.isEmpty())
  coVerify(exactly = 0) { dao.getAllGenres() }
 }

 @Test
 fun `getCachedGenres should return empty when cache is expired`() = runTest {
  val expiredTime = System.currentTimeMillis() - SeriesRoomLocalDateSource.CACHE_VALIDITY_DURATION_MS - 1
  coEvery { cacheDao.getCacheForKeyword("genres") } returns SearchCacheDto("genres", expiredTime)

  val result = dataSource.getCachedGenres()

  assertTrue(result.isEmpty())
  coVerify(exactly = 0) { dao.getAllGenres() }
 }

 @Test
 fun `clearAllData should clear all series, seasons, genres, and cache`() = runTest {
  dataSource.clearAllData()

  coVerify { dao.clearAllSeries() }
  coVerify { dao.clearAllSeasons() }
  coVerify { dao.clearAllGenres() }
  coVerify { cacheDao.clearAll() }
 }
  @Test
  fun `getRecentSeries should return recent series`() = runTest {
   coEvery { dao.getRecentSeries() } returns sampleSeries

   val result = dataSource.getRecentSeries()

   assertEquals(sampleSeries, result)
   coVerify { dao.getRecentSeries() }
  }

  @Test
  fun `storeRecentSeries should mark series as viewed`() = runTest {
   dataSource.storeRecentSeries(1)

   coVerify { dao.markSeriesAsViewed(1) }
  }

  @Test
  fun `clearRecentSeries should call DAO to clear`() = runTest {
   dataSource.clearRecentSeries()

   coVerify { dao.clearRecentSeries() }
  }

  @Test
  fun `getSeasonsForSeries should return list of seasons`() = runTest {
   every { dao.getSeasonsForSeries(1) } returns flowOf(sampleSeasons)

   val result = dataSource.getSeasonsForSeries(1)

   assertEquals(sampleSeasons, result)
   coVerify { dao.getSeasonsForSeries(1) }
  }


 }
