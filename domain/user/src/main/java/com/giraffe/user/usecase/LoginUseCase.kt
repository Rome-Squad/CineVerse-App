package com.giraffe.user.usecase

import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidEmailException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(userInput: String, password: String) {
        if (userInput.contains('@')) validateEmail(userInput)
        else validateUsername(userInput)

        validatePassword(password)
        authRepository.login(userInput, password)
    }

    private fun validateEmail(email: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if (!emailRegex.matches(email)) throw InvalidEmailException()
    }

    private fun validateUsername(username: String) {
        if (username.isBlank()) throw EmptyUsernameException()
    }

    private fun validatePassword(password: String) {
        if (password.length < 4) throw InvalidPasswordException()
    }
}