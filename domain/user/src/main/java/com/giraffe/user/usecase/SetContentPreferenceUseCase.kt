package com.giraffe.user.usecase

import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject

class SetContentPreferenceUseCase @Inject constructor(private val repo: SettingsRepository) {
    suspend operator fun invoke(preference: ContentPreference) =
        repo.setContentPreference(preference)
}