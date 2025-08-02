package com.giraffe.user.usecase

import com.giraffe.user.repository.OnboardingRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class SetOnBoardingFirstTimeUseCaseTest {

    private lateinit var onboardingRepository: OnboardingRepository
    private lateinit var setOnBoardingFirstTimeUseCase: SetOnBoardingFirstTimeUseCase

    @BeforeEach
    fun setUp() {
        onboardingRepository = mockk(relaxed = true)
        setOnBoardingFirstTimeUseCase = SetOnBoardingFirstTimeUseCase(onboardingRepository)
    }

    @Test
    fun `should call repository setOnBoardingFirstTime`() = runTest {
        setOnBoardingFirstTimeUseCase()

        coVerify(exactly = 1) { onboardingRepository.setFirstTimeStatus() }
    }

}