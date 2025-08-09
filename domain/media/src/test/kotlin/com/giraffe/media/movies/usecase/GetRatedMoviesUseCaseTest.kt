package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.user.usecase.GetUserUseCase
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

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

//    @Test
//    fun `invoke should return rated movies for user`() = runTest {
//        // Given
//        val accountId = 123
//        val mockUser = User(
//            id = accountId,
//            displayName = "",
//            username = "",
//            avatarUrl = ""
//        )
//        val expectedRatedMovies = mapOf(
//            9.0f to fakeMovie(id = 1, title = "Inception"),
//            8.5f to fakeMovie(id = 2, title = "Interstellar")
//        )
//
//        coEvery { getUserUseCase() } returns mockUser
//        coEvery { repository.getRatedMovies(accountId) } returns expectedRatedMovies
//
//        // When
//        val result = useCase()
//
//        // Then
//        coVerify { getUserUseCase() }
//        coVerify { repository.getRatedMovies(accountId) }
//        assertEquals(expectedRatedMovies, result)
//    }
}
