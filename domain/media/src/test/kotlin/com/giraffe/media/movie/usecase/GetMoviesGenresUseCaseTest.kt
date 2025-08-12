package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test


class GetMoviesGenresUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var getMoviesGenresUseCase: GetMoviesGenresUseCase =
        GetMoviesGenresUseCase(repository)

    @Test
    fun `invoke should call getGenres on repository`() = runTest {
        //given
        coEvery { repository.getGenres() } returns emptyList()

        //when
        getMoviesGenresUseCase()

        //then
        coVerify(exactly = 1) { repository.getGenres() }
    }

    @Test
    fun `invoke should return list of genres from repository`() = runTest {
        // given
        coEvery { repository.getGenres() } returns fakeGenres

        // when
        val result = getMoviesGenresUseCase()

        // then
        assertThat(result).isEqualTo(fakeGenres)
    }
}