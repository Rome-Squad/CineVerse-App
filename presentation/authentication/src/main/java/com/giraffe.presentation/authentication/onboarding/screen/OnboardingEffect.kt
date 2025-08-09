package com.giraffe.presentation.authentication.onboarding.screen


sealed class OnboardingEffect {
    object NavigateToLogin : OnboardingEffect()
    data class ShowError(val throwable: Throwable) : OnboardingEffect()
}