package com.giraffe.series.usecase


import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecentSeriesGenresTest {

 private lateinit var repository: SeriesRepository
 private lateinit var useCase: GetRecentSeriesGenres

 @BeforeEach
 fun setUp() {
  repository = mockk()
  useCase = GetRecentSeriesGenres(repository)
 }

 @Test
 fun `should return recent series from repository`() = runTest {
  // Given
  val expectedSeries = listOf(
   Series(id = 1, name = "Loki", overview = "", rating = 8.5f, duration = "", posterUrl = "", genreIDs = listOf(1, 2), releaseYear = "2021", seasons = emptyList()),
   Series(id = 2, name = "Moon Knight", overview = "", rating = 8.2f, duration = "", posterUrl = "", genreIDs = listOf(3), releaseYear = "2022", seasons = emptyList())
  )
  coEvery { repository.getRecentSeries() } returns expectedSeries

  // When
  val result = useCase()

  // Then
  assertEquals(expectedSeries, result)
  coVerify(exactly = 1) { repository.getRecentSeries() }
 }
}