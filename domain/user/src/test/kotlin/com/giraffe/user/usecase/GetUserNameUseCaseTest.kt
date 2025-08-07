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
import kotlin.test.assertFailsWith

class GetUserNameUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var getUserNameUseCase: GetUserNameUseCase

    private val fakeUser = User(
        id = 1,
        displayName = "hamada rayyan",
        username = "hamada_rayyan",
        avatarUrl = null
    )

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        getUserNameUseCase = GetUserNameUseCase(userRepository)
    }

    @Test
    fun `invoke should call getUser on repository`() = runTest {
        // Given
        coEvery { userRepository.getUser() } returns fakeUser

        // When
        getUserNameUseCase()

        // Then
        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `invoke when user has non-empty displayName should return displayName`() = runTest {
        // Given
        coEvery { userRepository.getUser() } returns fakeUser

        // When
        val result = getUserNameUseCase()

        // Then
        assertThat(result).isEqualTo(fakeUser.displayName)
    }

    @Test
    fun `invoke when user has empty displayName should return username`() = runTest {
        // Given
        val userWithoutDisplayName = fakeUser.copy(displayName = "")
        coEvery { userRepository.getUser() } returns userWithoutDisplayName

        // When
        val result = getUserNameUseCase()

        // Then
        assertThat(result).isEqualTo(fakeUser.username)
    }

    @Test
    fun `invoke when repository throws exception should rethrow the exception`() = runTest {
        // Given
        val expectedException = IOException("Network failed")
        coEvery { userRepository.getUser() } throws expectedException

        // When
        val actualException = assertFailsWith<IOException> {
            getUserNameUseCase()
        }

        // Then
        assertThat(actualException).isEqualTo(expectedException)
    }
}