package com.giraffe.user.usecase

import com.giraffe.user.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GetUserNameUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<String> {
        return getUserUseCase().transform { user ->
            if (user == null) {
                val refreshedUser = userRepository.refreshUser()
                emit(refreshedUser.username)
            } else {
                val name = if (user.displayName.isEmpty()) {
                    user.username
                } else {
                    user.displayName
                }
                emit(name)
            }
        }
    }
}
