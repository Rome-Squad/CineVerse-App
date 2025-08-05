package com.giraffe.cineverseapp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.user.usecase.GetDarkModeUseCase
import com.giraffe.user.usecase.GetLanguageUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
import com.giraffe.user.usecase.IsOnBoardingFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val isOnBoardingFirstTimeUseCase: IsOnBoardingFirstTimeUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        checkIfOnBoardingFirstTime()
        checkIsLoggedIn()
        observeTheme()
        observeLanguage()
    }


    private fun checkIfOnBoardingFirstTime() {
        safeExecute(
            onSuccess = ::onCheckOnBoardingFirstTimeSuccess
        ) {
            isOnBoardingFirstTimeUseCase()
        }
    }

    private fun onCheckOnBoardingFirstTimeSuccess(result: Boolean) {
        _state.value = _state.value.copy(
            isOnBoardingFirstTime = result,
        )
    }

    private fun checkIsLoggedIn() {
        safeExecute(
            onSuccess = ::onCheckLoginSuccess
        ) {
            isLoggedInUseCase()
        }
    }

    private fun onCheckLoginSuccess(result: Boolean) {
        _state.value = _state.value.copy(
            isLoggedIn = result,
        )
    }

    private fun observeTheme() {
        getDarkModeUseCase()
            .onEach { isDark ->
                _state.value = _state.value.copy(isDarkMode = isDark)
            }
            .launchIn(viewModelScope)
    }

    private fun observeLanguage() {
        getLanguageUseCase()
            .onEach { lang ->
                _state.value = _state.value.copy(language = lang)
            }
            .launchIn(viewModelScope)
    }

    private inline fun <T> safeExecute(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        crossinline onSuccess: suspend (T) -> Unit,
        crossinline block: suspend CoroutineScope.() -> T
    ): Job {
        return coroutineScope.launch(dispatcher) {
            val result = block()
            onSuccess(result)
        }
    }
}