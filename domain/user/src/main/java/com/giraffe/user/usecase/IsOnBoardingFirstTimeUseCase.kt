package com.giraffe.user.usecase

import com.giraffe.user.repository.OnboardingRepository
import javax.inject.Inject

class IsOnBoardingFirstTimeUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke() = onboardingRepository.isFirstTime()
}