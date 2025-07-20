package com.giraffe.authentication.screen

data class LoginState(

    val email: String = "",
    val password: String = "",

    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,

    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)