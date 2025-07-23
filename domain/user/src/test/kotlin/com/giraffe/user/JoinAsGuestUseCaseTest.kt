package com.giraffe.user

import com.giraffe.user.repository.AuthRepository
import com.giraffe.user.usecase.JoinAsGuestUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class JoinAsGuestUseCaseTest {
    private lateinit var joinAsGuestUseCase: JoinAsGuestUseCase
    private lateinit var authRepository: AuthRepository

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        joinAsGuestUseCase = JoinAsGuestUseCase(authRepository)
    }

    @Test
    fun `should call joinAsGuest on AuthRepository`() = runTest {
        // Given
        val sessionId = "645d4d3b181df9be16fa6a57fca802b2"

        coEvery { authRepository.joinAsGuest() } returns sessionId

        // When
        joinAsGuestUseCase()

        // Then
        coVerify(exactly = 1) { authRepository.joinAsGuest() }
    }

    @Test
    fun `should return session ID when calling joinAsGuest on AuthRepository`() = runTest {
        // Given
        val sessionId = "645d4d3b181df9be16fa6a57fca802b2"

        coEvery { authRepository.joinAsGuest() } returns sessionId

        // When
        val result = joinAsGuestUseCase()

        // Then
        assertEquals(sessionId, result)
    }
}