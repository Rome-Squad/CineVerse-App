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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetRecentlyReleasedMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetRecentlyReleasedMoviesUseCase =
        GetRecentlyReleasedMoviesUseCase(repository)

    @Test
    fun `invoke should call getRecentlyReleased on repository`() = runTest {        // given
        coEvery { repository.getRecentlyReleased(any(), any()) } returns emptyFlow()
        // when
        useCase(page, limit)

        // then
        coVerify(exactly = 1) { repository.getRecentlyReleased(any(), any()) }
    }

    @Test
    fun `given recently released movies, when invoke is called, then return movie list`() =
        runTest {
            // Given
            val expectedMovies = flow { emit(fakeMovies) }
            coEvery { repository.getRecentlyReleased(page, limit) } returns expectedMovies

            // When
            val actualMovies = useCase(page, limit)

            // Then
            assertThat(actualMovies).isEqualTo(expectedMovies)
        }
}

