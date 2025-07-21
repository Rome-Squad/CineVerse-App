package com.giraffe.authentication.screen

import com.giraffe.authentication.validation.ValidationError

data class LoginState(

    val email: String = "",
    val password: String = "",

    val emailErrorMessage: ValidationError? = null,
    val passwordErrorMessage: ValidationError? = null,

    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isVisibleCreateNewAccountBottomSheet: Boolean = false,
)