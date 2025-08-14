package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetPopularityMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetPopularMoviesUseCase = GetPopularMoviesUseCase(repository)

    @Test
    fun `invoke should call getPopular on repository`() = runTest {
        // given
        coEvery { repository.getPopular(any(), any()) } returns emptyList()

        // when
        useCase(page, limit)

        // then
        coVerify(exactly = 1) { repository.getPopular(any(), any()) }
    }

    @Test
    fun `given popular movies, when invoke is called, then return movie list`() = runTest {
        // Given
        val expectedPopularityMovies = fakeMovies.filter { it.popularity > 0 }
        coEvery { repository.getPopular(page, limit) } returns expectedPopularityMovies

        // When
        val actualMovies = useCase(page, limit)

        // Then
        assertThat(actualMovies).isEqualTo(expectedPopularityMovies)
    }
}
