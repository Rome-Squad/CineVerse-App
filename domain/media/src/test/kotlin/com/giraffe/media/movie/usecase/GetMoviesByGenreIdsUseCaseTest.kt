package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.page
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesByGenreIdsUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: GetMoviesByGenreIdsUseCase = GetMoviesByGenreIdsUseCase(repository)
    private val genreId = listOf(28)


    @Test
    fun `should return list of movies from repository for given genre ids`() = runTest {
        coEvery { repository.getByGenreIds(genreId, page) } returns fakeMovies

        val result = useCase(genreId, page)

        assertThat(result).isEqualTo(fakeMovies)
    }
}