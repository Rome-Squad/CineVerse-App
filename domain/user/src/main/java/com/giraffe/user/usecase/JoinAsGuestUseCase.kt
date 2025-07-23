package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository

class JoinAsGuestUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String {
        return authRepository.joinAsGuest()
    }
}