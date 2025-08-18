package com.giraffe.media.movie.usecase.upcoming

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

class ObserveUpcomingMoviesUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: ObserveUpcomingMoviesUseCase = ObserveUpcomingMoviesUseCase(repository)

    @Test
    fun `invoke should call observeUpcoming on repository`() = runTest {
        // given
        coEvery { repository.observeUpcoming(any()) } returns emptyFlow()

        // when
        useCase.invoke(limit)

        // then
        coVerify(exactly = 1) { repository.observeUpcoming(any()) }
    }

    @Test
    fun `given upcoming movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedMovies = flow { emit(fakeMovies) }
        coEvery { repository.observeUpcoming(limit) } returns expectedMovies

        // When
        val actualMovies = useCase.invoke(limit)

        // Then
        assertThat(actualMovies).isEqualTo(expectedMovies)
    }
}