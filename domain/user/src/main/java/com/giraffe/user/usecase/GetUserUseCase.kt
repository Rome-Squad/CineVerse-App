package com.giraffe.user.usecase

import com.giraffe.user.entity.User
import com.giraffe.user.repository.UserRepository
import jakarta.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        return authRepository.getUser()
    }
}