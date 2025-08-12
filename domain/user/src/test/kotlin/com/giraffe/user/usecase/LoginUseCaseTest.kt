package com.giraffe.user.usecase

import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.repository.AuthRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoginUseCaseTest {
    private val authRepository: AuthRepository = mockk(relaxed = true)
    val validateAuthInputUseCase: ValidateAuthInputUseCase = mockk(relaxed = true)
    private val loginUseCase: LoginUseCase = LoginUseCase(authRepository, validateAuthInputUseCase)

    @Test
    fun `should throw exception when username validation fails`() = runTest {
        val username = " "
        every { validateAuthInputUseCase.validateUsername(username) } throws EmptyUsernameException()

        assertThrows<EmptyUsernameException> {
            loginUseCase(username, "password")
        }
    }

    @Test
    fun `should NOT call login on repository when username validation fails`() = runTest {
        val username = " "
        every { validateAuthInputUseCase.validateUsername(username) } throws EmptyUsernameException()

        runCatching { loginUseCase(username, "password") }

        coVerify(exactly = 0) { authRepository.login(any(), any()) }
    }

    @Test
    fun `should call validateUsername on validation usecase`() = runTest {
        val username = "user"
        val password = "password"

        loginUseCase(username, password)

        verify(exactly = 1) { validateAuthInputUseCase.validateUsername(username) }
    }

    @Test
    fun `should call validatePassword on validation usecase`() = runTest {
        val username = "user"
        val password = "password"

        loginUseCase(username, password)

        verify(exactly = 1) { validateAuthInputUseCase.validatePassword(password) }
    }

    @Test
    fun `should call login on repository when validation is successful`() = runTest {
        val username = "user"
        val password = "password"

        loginUseCase(username, password)

        coVerify(exactly = 1) { authRepository.login(username, password) }
    }
}
