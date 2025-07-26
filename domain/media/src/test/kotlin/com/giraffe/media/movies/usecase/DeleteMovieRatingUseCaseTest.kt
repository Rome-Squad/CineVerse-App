package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class DeleteMovieRatingUseCaseTest {
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var deleteMovieRatingUseCase: DeleteMovieRatingUseCase

    @BeforeEach
    fun setUp() {
        moviesRepository = mockk()
        deleteMovieRatingUseCase = DeleteMovieRatingUseCase(moviesRepository)
    }

    @Test
    fun `invoke should call deleteMovieRating on repository with correct movie id`() = runTest {
        // given
        val movieId = 123
        coEvery { moviesRepository.deleteMovieRating(movieId) } returns Unit

        // when
        deleteMovieRatingUseCase(movieId)

        // then
        coVerify(exactly = 1) { moviesRepository.deleteMovieRating(movieId) }
    }
}