package com.giraffe.user.usecase

import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(userInput: String, password: String) {
        validateUsername(userInput)
        validatePassword(password)

        authRepository.login(userInput, password)
    }

    private fun validateUsername(username: String) {
        if (username.isBlank()) throw EmptyUsernameException()
    }

    private fun validatePassword(password: String) {
        if (password.length < 4) throw InvalidPasswordException()
    }
}