package com.giraffe.presentation.details.navigation.main

import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.user.usecase.GetDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDarkModeUseCase: GetDarkModeUseCase
): BaseViewModel<DetailsScreensState, Any>(DetailsScreensState()) {
    init {
        safeCollect(
            onEmitNewValue = ::onDarkModeChange,
            block = getDarkModeUseCase::invoke
        )
    }

    private fun onDarkModeChange(isDarkTheme: Boolean) {
        updateState { it.copy(isDarkTheme = isDarkTheme) }
    }
}

data class DetailsScreensState(
    val isDarkTheme: Boolean = true,
)