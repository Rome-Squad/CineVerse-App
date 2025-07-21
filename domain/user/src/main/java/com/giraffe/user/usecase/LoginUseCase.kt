package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String) {
        authRepository.login(username, password)
    }
}