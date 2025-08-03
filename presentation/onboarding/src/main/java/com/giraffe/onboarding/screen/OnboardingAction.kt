package com.giraffe.onboarding.screen

sealed class OnboardingAction {
    object CheckIfFirstTime : OnboardingAction()
    object MarkOnboardingComplete : OnboardingAction()
}