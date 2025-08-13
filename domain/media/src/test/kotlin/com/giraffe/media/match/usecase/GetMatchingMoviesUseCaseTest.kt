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
    fun `invoke should call getMatchingMovies on repository with correct parameters`() = runTest {
        val genreIds = "28,12"
        val minRuntime = 90
        val maxRuntime = 120
        val earliestDate = "2020-01-01"
        val latestDate = "2023-01-01"

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
                latestDate
            )
        } returns expectedMovies

        val result = useCase(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestFirstAirDate = earliestDate,
            latestFirstAirDate = latestDate
        )

        coVerify(exactly = 1) {
            repository.getMatchingMovies(
                genreIds,
                minRuntime,
                maxRuntime,
                earliestDate,
                latestDate
            )
        }

        assertEquals(expectedMovies, result)
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        val genreIds = "18"

        coEvery {
            repository.getMatchingMovies(genreIds, null, null, null, null)
        } returns emptyList()

        val result = useCase(genreIds)

        assertEquals(emptyList<Movie>(), result)
    }
}
