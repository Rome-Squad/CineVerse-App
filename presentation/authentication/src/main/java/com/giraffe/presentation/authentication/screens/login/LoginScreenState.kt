package com.giraffe.presentation.authentication.screens.login

import androidx.compose.runtime.Stable

@Stable
data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val usernameErrorMessage: Int? = null,
    val passwordErrorMessage: Int? = null,
    val screenErrorMessage: Int? = null,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isGuestLoading: Boolean = false,
    val isNoInternet:Boolean=false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)