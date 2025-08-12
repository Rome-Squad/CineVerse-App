package com.giraffe.user.usecase

import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateAuthInputUseCase: ValidateAuthInputUseCase
) {
    suspend operator fun invoke(userInput: String, password: String) {
        validateAuthInputUseCase.validateUsername(userInput)
        validateAuthInputUseCase.validatePassword(password)

        authRepository.login(userInput, password)
    }

}