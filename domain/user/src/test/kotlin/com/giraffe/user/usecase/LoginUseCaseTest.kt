package com.giraffe.user.usecase

import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.exception.InvalidUsernameMatchException
import com.giraffe.user.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoginUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase

    @BeforeEach
    fun setup() {
        authRepository = mockk(relaxed = true)
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `should throw exception when username is blank`() = runTest {
        val username = ""
        val password = "validPass123"

        assertThrows<EmptyUsernameException> {
            loginUseCase(username, password)
        }
    }


    @Test
    fun `should throw exception when username is have space domain extension`() = runTest {
        val username = "user name"
        val password = "validPass123"

        assertThrows<InvalidUsernameMatchException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should throw exception when username contains invalid characters`() = runTest {
        val username = "user@exam!ple"
        val password = "validPass123"

        assertThrows<InvalidUsernameMatchException> {
            loginUseCase(username, password)
        }
    }


    @Test
    fun `should throw exception when password is empty`() = runTest {
        val username = "Hend25"
        val password = ""

        assertThrows<InvalidPasswordException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should throw exception when password is less than 4 characters`() = runTest {
        val username = "user123"
        val password = "123"

        assertThrows<InvalidPasswordException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should call login when username and password are valid`() = runTest {
        val username = "DoaaAbdelnaser"
        val password = "validPass123"

        coEvery { authRepository.login(username, password) } returns Unit

        loginUseCase(password, username)

        coVerify(exactly = 1) {
            authRepository.login(username, password)
        }
    }


    @Test
    fun `should accept valid username with subdomain`() = runTest {
        val username = "user12"
        val password = "securePassword9"

        coEvery { authRepository.login(username, password) } returns Unit

        loginUseCase(username, password)

        coVerify { authRepository.login(username, password) }
    }

    @Test
    fun `should accept valid password with special characters`() = runTest {
        val username = "user23"
        val password = "S3cur3#Pass!"

        coEvery { authRepository.login(username, password) } returns Unit

        loginUseCase(username, password)

        coVerify { authRepository.login(username, password) }
    }
}
