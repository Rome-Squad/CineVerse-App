package com.giraffe.presentation.authentication.screens.onboarding.screen

import com.giraffe.presentation.authentication.base.BaseViewModel
import com.giraffe.user.exception.NoInternetException
import com.giraffe.user.usecase.SetOnBoardingFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnBoardingFirstTimeUseCase: SetOnBoardingFirstTimeUseCase
) : BaseViewModel<OnboardingScreenState, OnboardingEffect>(OnboardingScreenState()),
    OnboardingInteractionListener {


    override fun markOnboardingComplete() {
        safeExecute(
            onSuccess = { completeOnboardingSuccess() },
            onError = this::onFailure
        ) {
            setOnBoardingFirstTimeUseCase()
        }
    }

    private fun onFailure(error: Throwable) {
        updateState { it.copy(isError = error is NoInternetException) }
        sendEffect(OnboardingEffect.ShowError(error))
    }
    private fun completeOnboardingSuccess() {
        updateState { it.copy(isError = false) }
        sendEffect(OnboardingEffect.NavigateToLogin)
    }
    }




