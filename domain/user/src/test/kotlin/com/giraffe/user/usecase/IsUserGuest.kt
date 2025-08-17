package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class IsUserGuestTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val isUserGuest = IsUserGuestUseCase(authRepository)

    @Test
    fun `should call isUserGuest method on authRepository`() = runTest {

        isUserGuest()

        coVerify(exactly = 1) { authRepository.isUserGuest() }
    }
}