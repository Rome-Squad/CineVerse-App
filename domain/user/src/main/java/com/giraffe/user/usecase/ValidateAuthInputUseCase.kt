package com.giraffe.user.usecase

import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.exception.InvalidUsernameMatchException
import jakarta.inject.Inject

class ValidateAuthInputUseCase @Inject constructor() {

    fun validateUsername(username: String) {
        if (username.isBlank()) throw EmptyUsernameException()
        if (username.contains(Regex("[^a-zA-Z0-9_-]"))) throw InvalidUsernameMatchException()
    }

    fun validatePassword(password: String) {
        if (password.length < 4) throw InvalidPasswordException()
    }
}