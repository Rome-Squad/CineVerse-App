package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.media.movie.util.limit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesByKeywordsIdUseCaseTest {
    private var repository: MovieRepository = mockk()
    private var useCase: GetMoviesByKeywordsIdUseCase = GetMoviesByKeywordsIdUseCase(repository)
    private val keywordId = 12345

    @Test
    fun `invoke should call getByKeywordsId on repository`() {
        runTest {
            // given
            coEvery { repository.getByKeywordsId(any(), any()) } returns emptyList()

            // when
            useCase.invoke(keywordId, limit)

            // then
            coVerify(exactly = 1) { repository.getByKeywordsId(any(), any()) }
        }
    }

    @Test
    fun `given movies for keyword, when invoke is called, then return movie list`() = runTest {
        // Given
        coEvery { repository.getByKeywordsId(keywordId, limit) } returns fakeMovies

        // When
        val actualMovies = useCase.invoke(keywordId, limit)

        // Then
        assertThat(actualMovies).isEqualTo(fakeMovies)
    }
}