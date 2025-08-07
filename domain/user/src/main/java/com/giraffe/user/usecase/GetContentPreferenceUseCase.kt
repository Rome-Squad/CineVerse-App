package com.giraffe.user.usecase

import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetContentPreferenceUseCase @Inject constructor(private val repo: SettingsRepository) {
    operator fun invoke(): Flow<ContentPreference> = repo.getContentPreference()
}