package com.giraffe.cineverseapp.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val isOnBoardingFirstTimeUseCase: IsOnBoardingFirstTimeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        checkIfOnBoardingFirstTime()
        checkIsLoggedIn()
    }


    fun checkIfOnBoardingFirstTime() {
        safeExecute(
            onSuccess = { result ->
                _state.value = _state.value.copy(
                    isOnBoardingFirstTime = result,
                )
            }
        ) {
            isOnBoardingFirstTimeUseCase()
        }
    }


    fun checkIsLoggedIn() {
        safeExecute(
            onSuccess = { result ->
                _state.value = _state.value.copy(
                    isLoggedIn = result,
                )
            }
        ) {
            isLoggedInUseCase()
        }
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