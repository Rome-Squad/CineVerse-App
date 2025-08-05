package com.giraffe.cineverseapp

import androidx.lifecycle.ViewModel
import com.giraffe.user.usecase.GetDarkModeUseCase
import com.giraffe.user.usecase.GetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getDarkModeUseCase: GetDarkModeUseCase,
    getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {
    val isDarkMode = getDarkModeUseCase()
    val language = getLanguageUseCase()
}