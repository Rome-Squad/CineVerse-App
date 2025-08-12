package com.giraffe.user.usecase

import com.giraffe.user.repository.OnboardingRepository
import javax.inject.Inject

class SetOnBoardingFirstTimeUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke() = onboardingRepository.setFirstTimeStatus()
}