package com.giraffe.user.usecase

import com.giraffe.user.exception.InvalidLoginException
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserNameUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) {
    operator fun invoke(): Flow<String> {
        return getUserUseCase().map { user ->
            user?.displayName?.ifEmpty { user.username } ?: throw InvalidLoginException()
        }
    }
}
