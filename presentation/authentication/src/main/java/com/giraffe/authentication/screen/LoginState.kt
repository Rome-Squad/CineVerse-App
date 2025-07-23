package com.giraffe.authentication.screen


data class LoginState(

    val username: String = "",
    val password: String = "",

    val usernameErrorMessage: Int? = null,
    val passwordErrorMessage: Int? = null,

    val isPasswordVisible: Boolean = false,
    val isLoadingLogin: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)