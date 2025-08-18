package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class IsLoggedInByAccountUseCaseTest {
    private val repository: AuthRepository = mockk(relaxed = true)
    private val isLoggedInByAccountUseCase: IsLoggedInByAccountUseCase =
        IsLoggedInByAccountUseCase(repository)

    @Test
    fun `should return that user logged in by account when repository return true`() = runTest {

        coEvery { repository.isLoggedInByAccount() } returns true

        val result = isLoggedInByAccountUseCase()

        assertThat(result).isTrue()
    }

    @Test
    fun `should return that user is not logged in by account when repository return false`() =
        runTest {

            coEvery { repository.isLoggedInByAccount() } returns false

            val result = isLoggedInByAccountUseCase()

            assertThat(result).isFalse()
        }
}