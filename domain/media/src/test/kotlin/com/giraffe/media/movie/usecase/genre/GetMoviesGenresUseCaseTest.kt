package com.giraffe.media.movie.usecase.genre

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeGenres
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesGenresUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: SyncMoviesGenresUseCase =
        SyncMoviesGenresUseCase(repository)

    @Test
    fun `invoke should call getGenres on repository`() = runTest {
        // given
        coEvery { repository.getGenres() } returns emptyList()

        // when
        useCase.invoke()

        // then
        coVerify(exactly = 1) { repository.getGenres() }
    }

    @Test
    fun `invoke should return genres from repository`() = runTest {
        // given
        coEvery { repository.getGenres() } returns fakeGenres

        // when
        val result = useCase.invoke()

        // then
        assertThat(result).isEqualTo(fakeGenres)
    }
}