package com.giraffe.media.movie.usecase.genre

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeGenres
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test


class ObserveMoviesGenresUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: ObserveMoviesGenresUseCase =
        ObserveMoviesGenresUseCase(repository)

    @Test
    fun `invoke should call getGenres on repository`() = runTest {
        //given
        coEvery { repository.observeGenres() } returns emptyFlow()

        //when
        useCase.invoke()

        //then
        coVerify(exactly = 1) { repository.observeGenres() }
    }

    @Test
    fun `invoke should return list of genres from repository`() = runTest {
        // given
        val expectedGenres = flowOf(fakeGenres)
        coEvery { repository.observeGenres() } returns expectedGenres

        // when
        val result = useCase.invoke()

        // then
        assertThat(result).isEqualTo(expectedGenres)
    }
}