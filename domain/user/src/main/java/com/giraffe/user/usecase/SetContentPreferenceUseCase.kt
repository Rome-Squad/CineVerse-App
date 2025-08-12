package com.giraffe.user.usecase

import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject

class SetContentPreferenceUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(preference: ContentPreference) =
        settingsRepository.setContentPreference(preference)
}