package com.giraffe.user.usecase

import com.giraffe.user.entity.AccountDetails
import com.giraffe.user.repository.AuthRepository
import jakarta.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AccountDetails {
        return authRepository.getAccountDetails()
    }
}