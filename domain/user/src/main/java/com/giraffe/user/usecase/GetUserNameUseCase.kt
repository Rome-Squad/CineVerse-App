package com.giraffe.user.usecase

import com.giraffe.user.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<String> {
        return userRepository.getUser().transform { user ->
            val userInfo = user ?: userRepository.refreshUser()
            emit(userInfo.displayName.ifEmpty { userInfo.username })
        }
    }
}
