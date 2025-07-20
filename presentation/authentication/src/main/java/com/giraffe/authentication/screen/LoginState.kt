package com.giraffe.authentication.screen

data class LoginState(

    val email: String = "",
    val password: String = "",

    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,

    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)