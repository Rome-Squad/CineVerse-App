package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MoviesRepository
import com.giraffe.user.entity.User
import com.giraffe.user.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetRatedMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var useCase: GetRatedMoviesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getUserUseCase = mockk()
        useCase = GetRatedMoviesUseCase(repository, getUserUseCase)
    }

    @Test
    fun `invoke should return rated movies for user`() = runTest {
        // Given
        val accountId = 123
        val mockUser = User(
            id = accountId,
            displayName = "",
            username = "",
            avatarUrl = ""
        )

        coEvery { getUserUseCase() } returns mockUser
        coEvery { repository.getUserRated(accountId) } returns fakeMovies

        // When
        val result = useCase()

        // Then
        coVerify { getUserUseCase() }
        coVerify { repository.getUserRated(accountId) }
        assertEquals(fakeMovies, result)
    }
}
