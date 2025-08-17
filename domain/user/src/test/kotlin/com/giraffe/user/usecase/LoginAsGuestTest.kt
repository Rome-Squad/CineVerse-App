package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


class LoginAsGuestTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val loginAsGuestUseCase: LoginAsGuestUseCase = LoginAsGuestUseCase(authRepository)

    @Test
    fun `should call loginAsGuest method on authRepository`() = runTest {
        loginAsGuestUseCase()

        coVerify(exactly = 1) { authRepository.loginAsGuest() }
    }
}