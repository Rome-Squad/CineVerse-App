package com.giraffe.repository

import com.giraffe.repository.datasource.local.SettingsLocalDataSource
import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun isDarkMode(): Flow<Boolean> {
        return localDataSource.isDarkMode()
    }

    override suspend fun setDarkMode(isDark: Boolean) {
        localDataSource.setDarkMode(isDark)
    }

    override fun getLanguage(): Flow<String> {
        return localDataSource.getLanguage()
    }

    override suspend fun setLanguage(languageCode: String) {
        localDataSource.setLanguage(languageCode)
    }
}