package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMatchesYourVibeMoviesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val useCase = GetMatchesYourVibeMoviesUseCase(repository)

    @Test
    fun `invoke should call getRemoteMatchesYourVibe on repository`() = runTest {
        // given
        coEvery { repository.getTopGenre() } returns null
        coEvery { repository.getRemoteMatchesYourVibe(any(), any()) } returns emptyList()

        // when
        useCase.getRemoteMatchesYourVibe(page, limit)

        // then
        coVerify(exactly = 1) { repository.getRemoteMatchesYourVibe(any(), any()) }
    }

    @Test
    fun `invoke should call getLocalMatchesYourVibe on repository`() = runTest {
        // given
        coEvery { repository.getTopGenre() } returns null
        coEvery { repository.getLocalMatchesYourVibe(limit) } returns flowOf(emptyList())

        // when
        useCase.getLocalMatchesYourVibe(limit)

        // then
        coVerify(exactly = 1) { repository.getLocalMatchesYourVibe(any()) }
    }

    @Test
    fun `invoke should return movies from remote repository when top genre is available`() =
        runTest {
            // Given
            coEvery { repository.getRemoteMatchesYourVibe(page, limit) } returns fakeMovies

            // When
            val actualMovies = useCase.getRemoteMatchesYourVibe(page, limit)

            // Then
            assertThat(actualMovies).isEqualTo(fakeMovies)
        }


    @Test
    fun `invoke should return movies from local repository when top genre is available`() =
        runTest {
            // Given
            val expectedMovies = flowOf(fakeMovies)
            coEvery { repository.getLocalMatchesYourVibe(limit) } returns expectedMovies

            // When
            val actualMovies = useCase.getLocalMatchesYourVibe(limit)

            // Then
            assertThat(actualMovies).isEqualTo(expectedMovies)
        }

    @Test
    fun `invoke should return empty list from remote repository when top genre is not found`() =
        runTest {
            // Given
            coEvery { repository.getRemoteMatchesYourVibe(page, limit) } returns emptyList()

            // When
            val actualMovies = useCase.getRemoteMatchesYourVibe(page, limit)

            // Then
            assertThat(actualMovies).isEmpty()
        }

    @Test
    fun `invoke should return empty list from local repository when top genre is not found`() =
        runTest {
            // Given
            coEvery { repository.getLocalMatchesYourVibe(limit) } returns flowOf(emptyList())

            // When
            val actualMovies = useCase.getLocalMatchesYourVibe(limit)

            // Then
            assertThat(actualMovies.first()).isEmpty()
        }
}