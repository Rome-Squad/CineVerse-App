package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test

class SetMovieRecentUseCaseTest {

    private val repository = mockk<MoviesRepository>(relaxed = true)
    private val useCase = SetMovieRecentUseCase(repository)

    @Test
    fun `GIVEN movie and isRecent WHEN invoked THEN repository is called with correct args`() =
        runTest {
            // Given
            val movie = Movie(
                id = 1,
                title = "Inception",
                description = "A mind-bending thriller.",
                rating = 8.8f,
                duration = 148,
                posterUrl = "https://example.com/inception.jpg",
                backdropUrl = "https://example.com/inception.jpg",
                youtubeVideoId = "abc123",
                genresID = listOf(1, 2, 3),
                releaseYear = LocalDate(2010, 7, 16),
                popularity = 0f
            )
            val isRecent = true

            // When
            useCase(movie, isRecent)

            // Then
            coVerify(exactly = 1) {
                repository.setMovieRecent(movie = movie, isRecent = isRecent)
            }
        }
}
