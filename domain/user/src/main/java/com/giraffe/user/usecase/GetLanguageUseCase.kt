package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<String> = settingsRepository.getLanguage()
}