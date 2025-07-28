package com.giraffe.user.usecase

import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.exception.InvalidUsernameOrPasswordException
import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(userInput: String, password: String) {
        validateUsername(userInput)
        validatePassword(password)

        authRepository.login(userInput, password)
    }

    private fun validateUsername(username: String) {
        if (username.isBlank()) throw EmptyUsernameException()
        if (username.contains(Regex("[^a-zA-Z0-9_-]"))) throw InvalidUsernameOrPasswordException()
    }

    private fun validatePassword(password: String) {
        if (password.length < 4) throw InvalidPasswordException()
    }
}