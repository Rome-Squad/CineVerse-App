package com.giraffe.media.movie.usecase.upcoming

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetUpcomingMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(repository)

    @Test
    fun `invoke should call getUpcoming on repository`() = runTest {
        // given
        coEvery { repository.getUpcoming(any(), any()) } returns emptyList()

        // when
        useCase.invoke(page, limit)

        // then
        coVerify(exactly = 1) { repository.getUpcoming(any(), any()) }
    }

    @Test
    fun `given upcoming movies, when invoke is called, then return movie list`() = runTest {
        // Given
        coEvery { repository.getUpcoming(page, limit) } returns fakeMovies

        // When
        val actualMovies = useCase.invoke(page, limit)

        // Then
        assertThat(actualMovies).isEqualTo(fakeMovies)
    }
}
