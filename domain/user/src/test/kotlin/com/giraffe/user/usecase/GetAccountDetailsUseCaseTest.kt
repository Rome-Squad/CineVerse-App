package com.giraffe.user.usecase

import com.giraffe.user.entity.AccountDetails
import com.giraffe.user.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetAccountDetailsUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var getAccountDetailsUseCase: GetAccountDetailsUseCase

    val expectedDetails = AccountDetails(
        id = 1,
        displayName = "Test User",
        username = "testuser",
        avatarUrl = "https://example.com/avatar.jpg"
    )

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        getAccountDetailsUseCase = GetAccountDetailsUseCase(authRepository)
    }

    @Test
    fun `invoke should call getAccountDetails on repository`() = runTest {
        // given
        coEvery { authRepository.getAccountDetails() } returns expectedDetails

        // when
        getAccountDetailsUseCase()

        // then
        coVerify(exactly = 1) { authRepository.getAccountDetails() }
    }

    @Test
    fun `invoke() should return account details from repository`() = runTest {
        // given
        coEvery { authRepository.getAccountDetails() } returns expectedDetails

        // when
        val result = getAccountDetailsUseCase()

        // then
        assertThat(result).isEqualTo(expectedDetails)
    }

}