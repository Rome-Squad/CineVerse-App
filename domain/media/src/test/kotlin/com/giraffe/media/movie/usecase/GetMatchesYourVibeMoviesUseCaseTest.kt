package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMatchesYourVibeMoviesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val useCase = GetMatchesYourVibeMoviesUseCase(repository)

    @Test
    fun `invoke should call getMatchesYourVibe on repository`() = runTest {
        // given
        coEvery { repository.getMatchesYourVibe(any(), any()) } returns emptyFlow()

        // when
        useCase.invoke(page, limit)

        // then
        coVerify(exactly = 1) { repository.getMatchesYourVibe(any(), any()) }
    }

    @Test
    fun `invoke should return movies from repository when top genre is available`() =
        runTest {
            // Given
            val expectedMovies = flowOf(fakeMovies)
            coEvery { repository.getMatchesYourVibe(page, limit) } returns expectedMovies

            // When
            val actualMovies = useCase.invoke(page, limit)

            // Then
            assertThat(actualMovies).isEqualTo(expectedMovies)
        }
}