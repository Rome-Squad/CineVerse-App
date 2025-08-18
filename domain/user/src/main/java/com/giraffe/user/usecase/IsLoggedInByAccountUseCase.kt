package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class IsLoggedInByAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.isLoggedInByAccount()
}