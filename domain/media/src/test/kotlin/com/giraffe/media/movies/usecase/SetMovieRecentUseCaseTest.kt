package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SetMovieRecentUseCaseTest {

    private val repository = mockk<MoviesRepository>(relaxed = true)
    private val useCase = SetMovieRecentUseCase(repository)

    @Test
    fun `GIVEN movie and isRecent WHEN invoked THEN repository is called with correct args`() =
        runTest {
            // Given
            val movie = fakeMovie(id = 1, title = "Inception")
            val isRecentViewed = System.currentTimeMillis()

            // When
            useCase(movie, isRecentViewed)

            // Then
            coVerify(exactly = 1) {
                repository.setMovieRecent(movie = movie, isRecentViewed = isRecentViewed)
            }
        }
}
