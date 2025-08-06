package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject

class SetDarkModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(isDark: Boolean) = settingsRepository.setDarkMode(isDark)
}