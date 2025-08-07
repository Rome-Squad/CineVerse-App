package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.repository.MoviesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMovieGenresUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetMoviesGenresByIdsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetMoviesGenresByIdsUseCase(repository)
    }

    @Test
    fun `should return list of movie genres from repository`() = runTest {
        // Given
        val expectedGenres = listOf(
            Genre(
                id = 1, title = "Action",
                rank = 0
            ),
            Genre(
                id = 2, title = "Comedy",
                rank = 0
            )
        )
        coEvery { repository.getMovieGenresByIds(listOf(1, 2)) } returns expectedGenres

        // When
        val result = useCase(listOf(1, 2))

        // Then
        coVerify { repository.getMovieGenresByIds(listOf(1, 2)) }
        assertThat(result).isEqualTo(expectedGenres)
    }
}