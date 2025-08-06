package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(languageCode: String) = settingsRepository.setLanguage(languageCode)
}