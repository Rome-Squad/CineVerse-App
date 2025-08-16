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

class GetRecentlyReleasedMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetRecentlyReleasedMoviesUseCase =
        GetRecentlyReleasedMoviesUseCase(repository)

    @Test
    fun `invoke should call getRemoteRecentlyReleased on repository`() = runTest {
        // given
        coEvery { repository.getRemoteRecentlyReleased(any(), any()) } returns emptyList()

        // when
        useCase.getRemoteRecentlyReleased(page, limit)

        // then
        coVerify(exactly = 1) { repository.getRemoteRecentlyReleased(any(), any()) }
    }

    @Test
    fun `invoke should call getLocalRecentlyReleased on repository`() = runTest {
        // given
        coEvery { repository.getLocalRecentlyReleased(any()) } returns flowOf(emptyList())

        // when
        useCase.getLocalRecentlyReleased(limit)

        // then
        coVerify(exactly = 1) { repository.getLocalRecentlyReleased(any()) }
    }

    @Test
    fun `given remote recently released movies, when invoke is called, then return movie list`() =
        runTest {
            // Given
            coEvery { repository.getRemoteRecentlyReleased(page, limit) } returns fakeMovies

            // When
            val actualMovies = useCase.getRemoteRecentlyReleased(page, limit)

            // Then
            assertThat(actualMovies).isEqualTo(fakeMovies)
        }

    @Test
    fun `given local recently released movies, when invoke is called, then return movie list`() =
        runTest {
            // Given
            val expectedMovies = flow { emit(fakeMovies) }
            coEvery { repository.getLocalRecentlyReleased(limit) } returns expectedMovies

            // When
            val actualMovies = useCase.getLocalRecentlyReleased(limit)

            // Then
            assertThat(actualMovies).isEqualTo(expectedMovies)
        }
}

