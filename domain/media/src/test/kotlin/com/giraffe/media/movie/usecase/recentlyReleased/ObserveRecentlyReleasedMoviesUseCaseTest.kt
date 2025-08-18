package com.giraffe.media.movie.usecase.recentlyReleased

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ObserveRecentlyReleasedMoviesUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: ObserveRecentlyReleasedMoviesUseCase =
        ObserveRecentlyReleasedMoviesUseCase(repository)

    @Test
    fun `invoke should call observeRecentlyReleased on repository`() = runTest {
        // given
        coEvery { repository.observeRecentlyReleased(any()) } returns emptyFlow()

        // when
        useCase.invoke(limit)

        // then
        coVerify(exactly = 1) { repository.observeRecentlyReleased(any()) }
    }

    @Test
    fun `given recently released movies, when invoke is called, then return movie list`() =
        runTest {
            // Given
            val expectedMovies = flow { emit(fakeMovies) }
            coEvery { repository.observeRecentlyReleased(limit) } returns expectedMovies

            // When
            val actualMovies = useCase.invoke(limit)

            // Then
            assertThat(actualMovies).isEqualTo(expectedMovies)
        }
}