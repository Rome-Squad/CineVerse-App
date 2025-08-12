package com.giraffe.user.usecase

import jakarta.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): String {
        val user = getUserUseCase.invoke()
        val name = if (user.displayName.isEmpty()) {
            user.username
        } else {
            user.displayName
        }
        return name
    }
}
