package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeGenres
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMovieGenresUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var useCase: GetMoviesGenresByIdsUseCase = GetMoviesGenresByIdsUseCase(repository)

    private val genreIds = listOf(1, 2)

    @Test
    fun `invoke should call getGenresByIds on repository`() = runTest {
        // given
        coEvery { repository.getGenresByIds(any()) } returns flowOf(emptyList())

        // when
        useCase(genreIds)

        // then
        coVerify(exactly = 1) { repository.getGenresByIds(any()) }
    }

    @Test
    fun `should return list of movie genres from repository`() = runTest {
        // Given
        val expectedGenres = flowOf(fakeGenres)
        coEvery { repository.getGenresByIds(genreIds) } returns expectedGenres

        // When
        val result = useCase(genreIds)

        // Then
        assertThat(result).isEqualTo(expectedGenres.first())
    }
}