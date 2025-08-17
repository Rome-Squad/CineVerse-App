package com.giraffe.media.movie.usecase.matchesYourVibe

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ObserveMatchesYourVibeMoviesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val useCase: ObserveMatchesYourVibeMoviesUseCase =
        ObserveMatchesYourVibeMoviesUseCase(repository)

    @Test
    fun `invoke should call observeMatchesYourVibe on repository`() = runTest {
        // given
        coEvery { repository.getTopGenre() } returns null
        coEvery { repository.observeMatchesYourVibe(limit) } returns emptyFlow()

        // when
        useCase.invoke(limit)

        // then
        coVerify(exactly = 1) { repository.observeMatchesYourVibe(any()) }
    }

    @Test
    fun `invoke should return movies from repository when top genre is available`() =
        runTest {
            // Given
            val expectedMovies = flowOf(fakeMovies)
            coEvery { repository.observeMatchesYourVibe(limit) } returns expectedMovies

            // When
            val actualMovies = useCase.invoke(limit)

            // Then
            assertThat(actualMovies).isEqualTo(expectedMovies)
        }
}