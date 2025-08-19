package com.giraffe.media.movie.usecase.matchesYourVibe

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.util.limit
import com.giraffe.media.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMatchesYourVibeMoviesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val useCase: GetMatchesYourVibeMoviesUseCase =
        GetMatchesYourVibeMoviesUseCase(repository)

    @Test
    fun `invoke should call getMatchesYourVibe on repository`() = runTest {
        // given
        coEvery { repository.getTopGenre() } returns null
        coEvery { repository.getMatchesYourVibe(any(), any()) } returns emptyList()

        // when
        useCase.invoke(page, limit)

        // then
        coVerify(exactly = 1) { repository.getMatchesYourVibe(any(), any()) }
    }

    @Test
    fun `invoke should return movies from repository when top genre is available`() =
        runTest {
            // Given
            coEvery { repository.getMatchesYourVibe(page, limit) } returns fakeMovies

            // When
            val actualMovies = useCase.invoke(page, limit)

            // Then
            assertThat(actualMovies).isEqualTo(fakeMovies)
        }

    @Test
    fun `invoke should return empty list from repository when top genre is not found`() =
        runTest {
            // Given
            coEvery { repository.getMatchesYourVibe(page, limit) } returns emptyList()

            // When
            val actualMovies = useCase.invoke(page, limit)

            // Then
            assertThat(actualMovies).isEmpty()
        }
}