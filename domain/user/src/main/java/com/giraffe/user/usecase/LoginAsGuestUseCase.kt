package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import jakarta.inject.Inject

class LoginAsGuestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.loginAsGuest()
}