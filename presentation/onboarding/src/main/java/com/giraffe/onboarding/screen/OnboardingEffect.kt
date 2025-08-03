package com.giraffe.onboarding.screen

import androidx.annotation.StringRes

sealed class OnboardingEffect {
    object NavigateToLogin : OnboardingEffect()
    object NavigateToHome : OnboardingEffect()
    data class ShowError(@param:StringRes val messageRes: Int) : OnboardingEffect()
}