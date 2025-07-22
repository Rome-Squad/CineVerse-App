package com.giraffe.user.usecase

import com.giraffe.user.exception.InvalidEmailException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) {
         validateEmail(email)
         validatePassword(password)
        authRepository.login(email, password)
    }

    private fun validateEmail(email: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if(!emailRegex.matches(email)) throw InvalidEmailException()

    }

    private fun validatePassword(password: String) {
        if(password.length < 8) throw InvalidPasswordException()
    }
}