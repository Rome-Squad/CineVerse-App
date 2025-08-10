package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMoviesByGenreUseCaseTest {

    private lateinit var repository: MovieRepository
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

        coEvery { repository.getByGenreId(genreId, 1) } returns expectedMovies

        // When
        val result = useCase(genreId, 1)

        // Then
        coVerify { repository.getByGenreId(genreId, 1) }
        assertThat(result).isEqualTo(expectedMovies)
    }
}