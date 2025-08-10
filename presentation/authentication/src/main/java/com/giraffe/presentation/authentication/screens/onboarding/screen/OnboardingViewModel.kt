package com.giraffe.presentation.authentication.screens.onboarding.screen

import com.giraffe.presentation.authentication.base.BaseViewModel
import com.giraffe.user.usecase.SetOnBoardingFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnBoardingFirstTimeUseCase: SetOnBoardingFirstTimeUseCase
) : BaseViewModel<OnboardingUiState, OnboardingEffect>(OnboardingUiState()),
    OnboardingInteractionListener {


    override fun markOnboardingComplete() {
        safeExecute(
            onSuccess = { completeOnboardingSuccess() },
            onError = ::completeOnboardingError
        ) {
            setOnBoardingFirstTimeUseCase()
        }
    }

    private fun completeOnboardingError(throwable: Throwable) {
        sendEffect(OnboardingEffect.ShowError(throwable))
    }
    private fun completeOnboardingSuccess() {
        updateState { it.copy(isError = false) }
        sendEffect(OnboardingEffect.NavigateToLogin)
    }
    }




