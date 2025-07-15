package com.giraffe.movies.usecase

import com.google.common.truth.Truth.assertThat
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test


class GetMoviesGenresUseCaseTest {
    private lateinit var repository: MoviesRepository
    private lateinit var getMoviesGenresUseCase: GetMoviesGenresUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        getMoviesGenresUseCase = GetMoviesGenresUseCase(repository)
    }

    @Test
    fun `invoke should call getMoviesGenres on repository`() = runTest {
        //given
        coEvery { repository.getMoviesGenres() } returns emptyList()

        //when
        getMoviesGenresUseCase()

        //then
        coVerify(exactly = 1) { repository.getMoviesGenres() }
    }

    @Test
    fun `invoke should return list of genres from repository`() = runTest {
        // given
        val expectedGenres = listOf(MovieGenre(1, "Action"), MovieGenre(2, "Comedy"))
        coEvery { repository.getMoviesGenres() } returns expectedGenres

        // when
        val result = getMoviesGenresUseCase()

        // then
        assertThat(result).isEqualTo(expectedGenres)
    }
}