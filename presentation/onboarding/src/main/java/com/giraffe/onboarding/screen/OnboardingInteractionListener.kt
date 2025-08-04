package com.giraffe.onboarding.screen


interface OnboardingInteractionListener {
    fun checkIfFirstTime()
    fun markOnboardingComplete()
    fun navigateToLoginScreen()
}