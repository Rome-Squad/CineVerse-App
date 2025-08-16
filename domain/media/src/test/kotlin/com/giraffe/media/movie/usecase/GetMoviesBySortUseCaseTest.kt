package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesBySortUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: GetMoviesBySortUseCase = GetMoviesBySortUseCase(repository)
    private val sortBy = "popularity.desc"

    @Test
    fun `invoke should call getBySort on repository`() {
        runTest {
            // given
            coEvery { repository.getBySort(any(), any()) } returns emptyList()

            // when
            useCase.invoke(sortBy, limit)

            // then
            coVerify(exactly = 1) { repository.getBySort(any(), any()) }
        }
    }

    @Test
    fun `given movies for sort by, when invoke is called, then return movie list`() = runTest {
        // Given
        coEvery { repository.getBySort(sortBy, limit) } returns fakeMovies

        // When
        val actualMovies = useCase.invoke(sortBy, limit)

        // Then
        assertThat(actualMovies).isEqualTo(fakeMovies)
    }
}