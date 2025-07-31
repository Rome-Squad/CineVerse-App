package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.isLoggedIn()
}