package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.user.entity.User
import com.giraffe.user.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetRatedMoviesUseCaseTest {

    private var repository: MovieRepository = mockk(relaxed = true)
    private var getUserUseCase: GetUserUseCase = mockk(relaxed = true)
    private var useCase: GetRatedMoviesUseCase = GetRatedMoviesUseCase(repository, getUserUseCase)
    private val accountId = 123
    private val mockUser = User(
        id = accountId,
        displayName = "",
        username = "",
        avatarUrl = ""
    )

    @Test
    fun `invoke should call getUserRated on repository`() = runTest {
        // given
        coEvery { repository.getUserRated(any()) } returns fakeMovies

        // when
        useCase()

        // then
        coVerify(exactly = 1) { repository.getUserRated(any()) }
    }

    @Test
    fun `invoke should call getUserUseCase on GetRatedMoviesUseCase`() = runTest {
        // given
        coEvery { getUserUseCase() } returns mockUser

        // when
        useCase()

        // then
        coVerify(exactly = 1) { getUserUseCase() }
    }

    @Test
    fun `invoke should return rated movies for user`() = runTest {
        // Given
        coEvery { getUserUseCase() } returns mockUser
        coEvery { repository.getUserRated(accountId) } returns fakeMovies

        // When
        val result = useCase()

        // Then
        assertEquals(fakeMovies, result)
    }
}
