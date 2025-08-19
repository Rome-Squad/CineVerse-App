package com.giraffe.user.usecase

import com.giraffe.user.entity.User
import com.giraffe.user.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<User?> {
        return userRepository.getUser()
    }
}