package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class IsLoggedInUseCaseTest {
    private lateinit var isLoggedInUseCase: IsLoggedInUseCase
    private val repository: AuthRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        isLoggedInUseCase = IsLoggedInUseCase(repository)
    }

    @Test
    fun `should return that user logged in when repository return true`() = runTest {
        //given
        coEvery { repository.isLoggedIn() } returns true
        //when
        val result = isLoggedInUseCase()
        //then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return that user is not logged in when repository return false`() = runTest {
        //given
        coEvery { repository.isLoggedIn() } returns false
        //when
        val result = isLoggedInUseCase()
        //then
        assertThat(result).isFalse()
    }
}