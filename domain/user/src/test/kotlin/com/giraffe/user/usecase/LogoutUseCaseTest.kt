package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogoutUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var logoutUseCase: LogoutUseCase

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        logoutUseCase = LogoutUseCase(authRepository)
    }

    @Test
    fun `invoke should call logout on repository`() = runTest {
        // given
        coEvery { authRepository.logout() } returns Unit

        // when
        logoutUseCase()

        // then
        coVerify(exactly = 1) { authRepository.logout() }
    }
}