package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AddMovieRatingUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var addMovieRatingUseCase: AddMovieRatingUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        addMovieRatingUseCase = AddMovieRatingUseCase(repository)
    }

    @Test
    fun `invoke should call addRating on repository with correct data`() = runTest {
        // given
        coEvery { repository.addRating(any(), any()) } returns Unit

        // when
        addMovieRatingUseCase(movieId = 1, ratingValue = 8.5f)

        // then
        coVerify(exactly = 1) { repository.addRating(1, 8.5f) }
    }
}