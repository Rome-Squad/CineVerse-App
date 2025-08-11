package com.giraffe.user.usecase

import com.giraffe.user.repository.OnboardingRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SetOnBoardingFirstTimeUseCaseTest {
    private var onboardingRepository: OnboardingRepository = mockk(relaxed = true)
    private var setOnBoardingFirstTimeUseCase: SetOnBoardingFirstTimeUseCase =
        SetOnBoardingFirstTimeUseCase(onboardingRepository)

    @Test
    fun `should call repository setOnBoardingFirstTime`() = runTest {
        setOnBoardingFirstTimeUseCase()

        coVerify(exactly = 1) { onboardingRepository.setFirstTimeStatus() }
    }

}