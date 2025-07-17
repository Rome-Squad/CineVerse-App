package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InsertGenresUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: InsertGenresUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = InsertGenresUseCase(repository)
    }

    @Test
    fun `should call repository to insert genres`() = runTest {
        // Given
        val genres = listOf(
            Genre(
                id = 1, title = "Action",
                rank = 0
            ),
            Genre(
                id = 2, title = "Drama",
                rank = 0
            )
        )

        // When
        useCase(genres)

        // Then
        coVerify { repository.insertGenres(genres) }
    }
}