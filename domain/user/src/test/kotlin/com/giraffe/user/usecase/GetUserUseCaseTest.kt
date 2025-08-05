package com.giraffe.user.usecase

import com.giraffe.user.entity.User
import com.giraffe.user.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetUserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUserUseCase: GetUserUseCase

    val expectedDetails = User(
        id = 1,
        displayName = "Test User",
        username = "testuser",
        avatarUrl = "https://example.com/avatar.jpg"
    )

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        getUserUseCase = GetUserUseCase(userRepository)
    }

    @Test
    fun `invoke should call getAccountDetails on repository`() = runTest {
        // given
        coEvery { userRepository.getUser() } returns expectedDetails

        // when
        getUserUseCase()

        // then
        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `invoke should return account details from repository`() = runTest {
        // given
        coEvery { userRepository.getUser() } returns expectedDetails

        // when
        val result = getUserUseCase()

        // then
        assertThat(result).isEqualTo(expectedDetails)
    }

}