package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GetRecentlyViewedMoviesUseCaseTest {
    private lateinit var repository: MoviesRepository

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
    }


    @Test
    fun `should returns recently watched movies`() = runTest {
        val expectedMovies = flow {
            emit(
                listOf(
                    Movie(
                        id = 1,
                        title = "Action Movie 1",
                        description = "Explosions and car chases",
                        rating = 7.8f,
                        duration = 120,
                        posterUrl = "https://example.com/action1.jpg",
                        backdropUrl = "https://example.com/action1.jpg",
                        youtubeVideoId = "abc123",
                        genresID = listOf(28),
                        releaseYear = LocalDate(2020, 6, 15),
                        popularity = 0f
                    ),
                    Movie(
                        id = 2,
                        title = "Action Movie 2",
                        description = "More explosions",
                        rating = 8.1f,
                        duration = 110,
                        posterUrl = "https://example.com/action2.jpg",
                        backdropUrl = "https://example.com/action1.jpg",
                        youtubeVideoId = "abc123",
                        genresID = listOf(28),
                        releaseYear = LocalDate(2021, 9, 5),
                        popularity = 0f
                    )
                )
            )
        }

        coEvery { repository.getRecentlyViewedMovies() } returns expectedMovies

        val useCase = GetRecentlyViewedMoviesUseCase(repository)

        val result = useCase()

        coVerify { repository.getRecentlyViewedMovies() }
        assertThat(result).isEqualTo(expectedMovies)

    }

}