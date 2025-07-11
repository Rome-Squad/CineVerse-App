package com.giraffe.movies.usecase
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMovieGenresUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetMoviesGenresUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetMoviesGenresUseCase(repository)
    }

    @Test
    fun `should return list of movie genres from repository`() = runTest {
        // Given
        val expectedGenres = listOf(
            MovieGenre(id = 1, title = "Action"),
            MovieGenre(id = 2, title = "Comedy")
        )
        coEvery { repository.getMoviesGenres() } returns expectedGenres

        // When
        val result = useCase()

        // Then
        coVerify { repository.getMoviesGenres() }
        assertThat(result).isEqualTo(expectedGenres)
    }
}