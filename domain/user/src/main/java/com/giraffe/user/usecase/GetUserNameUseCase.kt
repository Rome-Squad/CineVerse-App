package com.giraffe.user.usecase

import com.giraffe.user.repository.UserRepository
import jakarta.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String {
        val user = userRepository.getUser()
        val name = if (user.displayName.isEmpty()) {
            user.username
        } else {
            user.displayName
        }
        return name
    }
}
