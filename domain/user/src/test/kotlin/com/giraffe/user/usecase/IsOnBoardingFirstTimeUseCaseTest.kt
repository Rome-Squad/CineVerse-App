package com.giraffe.user.usecase


import com.giraffe.user.repository.OnboardingRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class IsOnBoardingFirstTimeUseCaseTest {
    private val onboardingRepository: OnboardingRepository = mockk()
    private val isOnBoardingFirstTimeUseCase: IsOnBoardingFirstTimeUseCase =
        IsOnBoardingFirstTimeUseCase(onboardingRepository)

    @Test
    fun `should return true when first time`() = runTest {

        coEvery { onboardingRepository.isFirstTime() } returns true

        val result = isOnBoardingFirstTimeUseCase()

        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when not first time`() = runTest {

        coEvery { onboardingRepository.isFirstTime() } returns false

        val result = isOnBoardingFirstTimeUseCase()

        assertThat(result).isFalse()
    }
}