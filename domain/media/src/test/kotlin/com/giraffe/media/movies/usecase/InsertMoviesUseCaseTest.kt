package com.giraffe.media.movies.usecase


import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InsertMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: InsertMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = InsertMoviesUseCase(repository)
    }

    @Test
    fun `should call repository to insert movies`() = runTest {
        // Given
        val movies = listOf(
            Movie(
                id = 1,
                title = "Movie 1",
                description = "Some description",
                rating = 7.5f,
                duration = 120,
                posterUrl = "https://example.com/movie1.jpg",
                backdropUrl = "https://example.com/movie1.jpg",
                youtubeVideoId = "abc123",
                genresID = listOf(1, 2),
                releaseYear = LocalDate(2022, 1, 1)
            )
        )

        // When
        useCase(movies)

        // Then
        coVerify { repository.insertMovies(movies) }
    }
}