package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class RefreshMoviesGenresUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: RefreshMoviesGenresUseCase = RefreshMoviesGenresUseCase(repository)

    @Test
    fun `invoke should call refreshGenres on repository`() = runTest {
        // given
        coEvery { repository.refreshGenres() } returns emptyList()

        // when
        useCase.invoke()

        // then
        coVerify(exactly = 1) { repository.refreshGenres() }
    }

}