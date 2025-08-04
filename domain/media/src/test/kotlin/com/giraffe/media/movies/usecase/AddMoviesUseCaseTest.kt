package com.giraffe.media.movies.usecase


import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: AddMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = AddMoviesUseCase(repository)
    }

    @Test
    fun `should call repository to insert movies`() = runTest {
        // Given
        val movies = fakeMovies

        // When
        useCase(movies)

        // Then
        coVerify { repository.addMovies(movies) }
    }
}