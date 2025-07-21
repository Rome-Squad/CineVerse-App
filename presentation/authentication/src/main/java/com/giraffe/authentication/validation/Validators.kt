package com.giraffe.authentication.validation


fun validateEmail(email: String): ValidationError? {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
    return if (!emailRegex.matches(email)) InvalidEmail else null
}

fun validatePassword(password: String): ValidationError? {
    return if (password.length < 8) InvalidPassword else null
}