package com.giraffe.authentication.onboarding.screen

import androidx.annotation.StringRes

sealed class OnboardingEffect {
    object NavigateToLogin : OnboardingEffect()
    data class ShowError(@param:StringRes val messageRes: Int) : OnboardingEffect()
}