package com.giraffe.media.match.usecase

import com.giraffe.media.match.repository.MatchRepository
import com.giraffe.media.movie.entity.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMatchingMoviesUseCaseTest {
    private val repository: MatchRepository = mockk(relaxed = true)
    private val useCase = GetMatchingMoviesUseCase(repository)

    @Test
    fun `invoke should call getMatchingMovies on repository with correct parameters including moodId`() =
        runTest {
            val genreIds = "28,12"
            val minRuntime = 90
            val maxRuntime = 120
            val earliestDate = "2020-01-01"
            val latestDate = "2023-01-01"
            val moodId = "9840|173824|10183"

            val expectedMovies = listOf<Movie>(
                mockk(relaxed = true),
                mockk(relaxed = true)
            )

            coEvery {
                repository.getMatchingMovies(
                    genreIds,
                    minRuntime,
                    maxRuntime,
                    earliestDate,
                    latestDate,
                    moodId
                )
            } returns expectedMovies

            val result = useCase(
                genreIds = genreIds,
                minRuntime = minRuntime,
                maxRuntime = maxRuntime,
                earliestFirstAirDate = earliestDate,
                latestFirstAirDate = latestDate,
                moodId = moodId
            )

            coVerify(exactly = 1) {
                repository.getMatchingMovies(
                    genreIds,
                    minRuntime,
                    maxRuntime,
                    earliestDate,
                    latestDate,
                    moodId
                )
            }

            assertEquals(expectedMovies, result)
        }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        val genreIds = "18"
        val moodId = ""

        coEvery {
            repository.getMatchingMovies(genreIds, null, null, null, null, moodId)
        } returns emptyList()

        val result = useCase(genreIds, moodId = moodId)

        assertEquals(emptyList<Movie>(), result)
    }
}
