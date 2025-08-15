package com.giraffe.user.usecase

import com.giraffe.user.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserNameUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<String> {
        return getUserUseCase().map { user ->
            if (user == null) {
                userRepository.refreshUser().username
            } else {
                if (user.displayName.isEmpty()) {
                    user.username
                } else {
                    user.displayName
                }
            }
        }
    }
}
