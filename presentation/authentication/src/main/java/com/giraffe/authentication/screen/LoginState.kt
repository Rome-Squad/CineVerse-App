package com.giraffe.authentication.screen


data class LoginState(

    val email: String = "",
    val password: String = "",

    val emailErrorMessage: Int? = null,
    val passwordErrorMessage: Int? = null,

    val isPasswordVisible: Boolean = false,
    val isLoadingLogin: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)