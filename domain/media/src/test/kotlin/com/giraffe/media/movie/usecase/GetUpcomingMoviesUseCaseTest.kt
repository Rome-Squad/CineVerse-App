package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetUpcomingMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(repository)

    @Test
    fun `invoke should call getRemoteUpcoming on repository`() = runTest {
        // given
        coEvery { repository.getRemoteUpcoming(any(), any()) } returns emptyList()

        // when
        useCase.getRemoteUpcoming(page, limit)

        // then
        coVerify(exactly = 1) { repository.getRemoteUpcoming(any(), any()) }
    }


    @Test
    fun `invoke should call getLocalUpcoming on repository`() = runTest {
        // given
        coEvery { repository.getLocalUpcoming(any()) } returns flowOf(emptyList())

        // when
        useCase.getLocalUpcoming(limit)

        // then
        coVerify(exactly = 1) { repository.getLocalUpcoming(any()) }
    }

    @Test
    fun `given remote upcoming movies, when invoke is called, then return movie list`() = runTest {
        // Given
        coEvery { repository.getRemoteUpcoming(page, limit) } returns fakeMovies

        // When
        val actualMovies = useCase.getRemoteUpcoming(page, limit)

        // Then
        assertThat(actualMovies).isEqualTo(fakeMovies)
    }

    @Test
    fun `given local upcoming movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedMovies = flow { emit(fakeMovies) }
        coEvery { repository.getLocalUpcoming(limit) } returns expectedMovies

        // When
        val actualMovies = useCase.getLocalUpcoming(limit)

        // Then
        assertThat(actualMovies).isEqualTo(expectedMovies)
    }
}
