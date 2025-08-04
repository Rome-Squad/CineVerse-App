package com.giraffe.user.usecase

import com.giraffe.user.entity.User
import com.giraffe.user.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import java.io.IOException
import kotlin.test.Test

class GetUserNameUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var getUserNameUseCase: GetUserNameUseCase

    val fakeUser = User(
        displayName = "Tarek",
        id = 123456,
        username = "hamada_rayyan",
        avatarUrl = ""
    )

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        getUserNameUseCase = GetUserNameUseCase(userRepository)
    }

    @Test
    fun `invoke() should call getUser on repository`() = runTest {
        // given
        coEvery { userRepository.getUser() } returns fakeUser

        // when
        getUserNameUseCase()

        // then
        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `invoke() when repository succeeds should return user's display name`() = runTest {
        // given
        val expectedName = "Tarek"
        coEvery { userRepository.getUser() } returns fakeUser

        // when
        val result = getUserNameUseCase()

        // then
        assertThat(result).isEqualTo(expectedName)
    }

    @Test
    fun `invoke when repository throws exception should return 'Guest'`() = runTest {
        // given
        coEvery { userRepository.getUser() } throws IOException("Network failed")

        // when
        val result = getUserNameUseCase()

        // then
        assertThat(result).isEqualTo("Guest")
    }
}