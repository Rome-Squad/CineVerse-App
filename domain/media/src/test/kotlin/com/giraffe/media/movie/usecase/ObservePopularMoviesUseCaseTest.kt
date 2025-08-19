package com.giraffe.media.movie.usecase

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

class ObservePopularMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: ObservePopularMoviesUseCase = ObservePopularMoviesUseCase(repository)

    @Test
    fun `invoke should call getPopular on repository`() = runTest {
        // given
        coEvery { repository.observePopular(any()) } returns emptyFlow()

        // when
        useCase(limit)

        // then
        coVerify(exactly = 1) { repository.observePopular(any()) }
    }

    @Test
    fun `given popular movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedPopularityMovies = flowOf(fakeMovies.filter { it.popularity > 0 })
        coEvery { repository.observePopular(limit) } returns expectedPopularityMovies

        // When
        val actualMovies = useCase(limit)

        // Then
        assertThat(actualMovies).isEqualTo(expectedPopularityMovies)
    }
}
