package com.giraffe.user.usecase

import com.giraffe.user.dummydata.fakeUser
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

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        getUserNameUseCase = GetUserNameUseCase(userRepository)
    }

    @Test
    fun `invoke should call getUser on repository`() = runTest {

    coEvery { userRepository.getUser() } returns fakeUser

        getUserNameUseCase()

        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `invoke when user has non-empty displayName should return displayName`() = runTest {

    coEvery { userRepository.getUser() } returns fakeUser

        val result = getUserNameUseCase()

        assertThat(result).isEqualTo(fakeUser.displayName)
    }

    @Test
    fun `invoke when user has empty displayName should return username`() = runTest {

    val userWithoutDisplayName = fakeUser.copy(displayName = "")
        coEvery { userRepository.getUser() } returns userWithoutDisplayName

        val result = getUserNameUseCase()

        assertThat(result).isEqualTo(fakeUser.username)
    }

    @Test
    fun `invoke when repository throws exception should rethrow the exception`() = runTest {

    val expectedException = IOException("Network failed")
        coEvery { userRepository.getUser() } throws expectedException

        val actualException = assertFailsWith<IOException> {
            getUserNameUseCase()
        }

        assertThat(actualException).isEqualTo(expectedException)
    }
}