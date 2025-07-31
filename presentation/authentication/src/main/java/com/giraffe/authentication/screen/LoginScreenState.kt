package com.giraffe.authentication.screen

import androidx.compose.runtime.Stable

@Stable
data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val usernameErrorMessage: Int? = null,
    val passwordErrorMessage: Int? = null,
    val screenErrorMessage: Int? = null,
    val isPasswordVisible: Boolean = false,
    val isLoadingLogin: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)