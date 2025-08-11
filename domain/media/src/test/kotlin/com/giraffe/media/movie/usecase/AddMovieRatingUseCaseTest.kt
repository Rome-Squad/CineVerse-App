package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AddMovieRatingUseCaseTest {

    private var repository: MovieRepository = mockk(relaxed = true)
    private var addMovieRatingUseCase: AddMovieRatingUseCase = AddMovieRatingUseCase(repository)

    @Test
    fun `invoke should call addRating on repository with correct data`() = runTest {
        // given
        coEvery { repository.addRating(any(), any()) } returns Unit

        // when
        addMovieRatingUseCase(movieId = movieId, rating = 8.5f)

        // then
        coVerify(exactly = 1) { repository.addRating(movieId, 8.5f) }
    }
}