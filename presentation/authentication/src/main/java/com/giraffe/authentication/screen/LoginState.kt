package com.giraffe.authentication.screen


data class LoginState(

    val email: String = "",
    val password: String = "",

    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",

    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)