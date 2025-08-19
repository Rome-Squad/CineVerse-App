package com.giraffe.media.movie.usecase.rate

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.movie.util.fakeMovies
import com.giraffe.user.entity.User
import com.giraffe.user.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetRatedMoviesUseCaseTest {

    private var repository: MovieRepository = mockk()
    private var getUserUseCase: GetUserUseCase = mockk()
    private var useCase: GetRatedMoviesUseCase = GetRatedMoviesUseCase(repository, getUserUseCase)
    private val mockUser = User(
        id = 123,
        displayName = "",
        username = "",
        avatarUrl = ""
    )

    @Test
    fun `invoke should call getUserRated on repository`() = runTest {

        coEvery { repository.getUserRated(any()) } returns emptyList()
        coEvery { getUserUseCase() } returns flowOf(mockUser)

        useCase()

        coVerify(exactly = 1) { repository.getUserRated(any()) }
    }

    @Test
    fun `invoke should call getUserUseCase on GetRatedMoviesUseCase`() = runTest {

        coEvery { repository.getUserRated(any()) } returns emptyList()
        coEvery { getUserUseCase() } returns flowOf(mockUser)

        useCase()

        coVerify(exactly = 1) { getUserUseCase() }
    }

    @Test
    fun `invoke should return rated movies for user`() = runTest {

        coEvery { getUserUseCase() } returns flowOf(mockUser)
        coEvery { repository.getUserRated(mockUser.id) } returns fakeMovies

        val result = useCase()

        assertEquals(fakeMovies, result)
    }
}
