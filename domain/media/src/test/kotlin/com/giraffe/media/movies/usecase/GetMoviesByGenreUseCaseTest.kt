package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMoviesByGenreUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetMoviesByGenresUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetMoviesByGenresUseCase(repository)
    }

    @Test
    fun `should return list of movies from repository for given genre id`() = runTest {
        // Given
        val genreId = 28
        val expectedMovies = fakeMovies

        coEvery { repository.getMoviesByGenre(genreId, 1) } returns expectedMovies

        // When
        val result = useCase(genreId, 1)

        // Then
        coVerify { repository.getMoviesByGenre(genreId, 1) }
        assertThat(result).isEqualTo(expectedMovies)
    }
}