package com.giraffe.user.usecase

import com.giraffe.user.exception.InvalidEmailException
import com.giraffe.user.exception.InvalidPasswordException
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

        assertThrows<InvalidEmailException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should throw exception when username is missing at symbol`() = runTest {
        val username = "bilal.com"
        val password = "validPass123"

        assertThrows<InvalidEmailException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should throw exception when username is missing domain extension`() = runTest {
        val username = "user@example"
        val password = "validPass123"

        assertThrows<InvalidEmailException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should throw exception when username contains invalid characters`() = runTest {
        val username = "user@exam!ple.com"
        val password = "validPass123"

        assertThrows<InvalidEmailException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should throw exception when password is empty`() = runTest {
        val username = "user@example.com"
        val password = ""

        assertThrows<InvalidPasswordException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should throw exception when password is less than 8 characters`() = runTest {
        val username = "user@example.com"
        val password = "1234567"

        assertThrows<InvalidPasswordException> {
            loginUseCase(username, password)
        }
    }

    @Test
    fun `should call login when username and password are valid`() = runTest {
        val username = "user@example.com"
        val password = "validPass123"

        coEvery {
            authRepository.login(
                username = username,
                password = password
            )
        } returns Unit

        loginUseCase(
            email = username,
            password = password
        )

        coVerify(exactly = 1) {
            authRepository.login(
                username = username,
                password = password
            )
        }
    }



    @Test
    fun `should accept valid username with subdomain`() = runTest {
        val username = "user@mail.example.co.uk"
        val password = "securePassword9"

        coEvery {
            authRepository.login(
                username = username,
                password = password
            )
        } returns Unit

        loginUseCase(
            email = username,
            password = password
        )

        coVerify {
            authRepository.login(
                username = username,
                password = password
            )
        }
    }

    @Test
    fun `should accept valid password with special characters`() = runTest {
        val username = "user@example.com"
        val password = "S3cur3#Pass!"

        coEvery {
            authRepository.login(
                username = username,
                password = password
            )
        } returns Unit

        loginUseCase(
            email = username,
            password = password
        )

        coVerify {
            authRepository.login(
                username = username,
                password = password
            )
        }
    }
}
