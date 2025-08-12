package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class IsLoggedInUseCaseTest {
    private val repository: AuthRepository = mockk(relaxed = true)
    private val isLoggedInUseCase: IsLoggedInUseCase = IsLoggedInUseCase(repository)

    @Test
    fun `should return that user logged in when repository return true`() = runTest {

        coEvery { repository.isLoggedIn() } returns true

        val result = isLoggedInUseCase()

        assertThat(result).isTrue()
    }

    @Test
    fun `should return that user is not logged in when repository return false`() = runTest {

        coEvery { repository.isLoggedIn() } returns false

        val result = isLoggedInUseCase()

        assertThat(result).isFalse()
    }
}