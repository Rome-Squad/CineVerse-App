package com.giraffe.authentication.screen


data class LoginState(

    val userInput: String = "",
    val password: String = "",

    val emailErrorMessage: Int? = null,
    val passwordErrorMessage: Int? = null,

    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)