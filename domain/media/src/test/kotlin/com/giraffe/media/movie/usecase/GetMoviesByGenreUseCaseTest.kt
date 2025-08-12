package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesByGenreUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetMoviesByGenresUseCase = GetMoviesByGenresUseCase(repository)
    private val genreId = 28

    @Test
    fun `invoke should call getByGenreId on repository`() = runTest {
        // given
        coEvery { repository.getByGenreId(any(), any()) } returns emptyList()

        // when
        val result = useCase(genreId, page)

        // then
        coVerify(exactly = 1) { repository.getByGenreId(any(), any()) }
    }

    @Test
    fun `should return list of movies from repository for given genre id`() = runTest {
        // Given
        coEvery { repository.getByGenreId(genreId, page) } returns fakeMovies

        // When
        val result = useCase(genreId, page)

        // Then
        assertThat(result).isEqualTo(fakeMovies)
    }
}