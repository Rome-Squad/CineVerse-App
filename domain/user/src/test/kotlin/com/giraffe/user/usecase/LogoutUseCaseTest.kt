package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class LogoutUseCaseTest {

    private var authRepository: AuthRepository = mockk()
    private var logoutUseCase: LogoutUseCase = LogoutUseCase(authRepository)

    @Test
    fun `invoke should call logout on repository`() = runTest {

    coEvery { authRepository.logout() } returns Unit

        logoutUseCase()

        coVerify(exactly = 1) { authRepository.logout() }
    }
}