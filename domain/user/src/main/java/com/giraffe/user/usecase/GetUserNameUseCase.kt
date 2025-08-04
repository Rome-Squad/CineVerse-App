package com.giraffe.user.usecase

import com.giraffe.user.repository.UserRepository
import jakarta.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String {
        return try {
            val user = userRepository.getUser()
            user.displayName
        } catch (e: Exception) {
            "Guest"
        }
    }
}
