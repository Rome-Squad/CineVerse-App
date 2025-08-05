package com.giraffe.user

import com.giraffe.repository.datasource.local.SettingsLocalDataSource
import com.giraffe.user.datastore.SettingsDataStore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsLocalDataSourceImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsLocalDataSource {

    override fun isDarkMode(): Flow<Boolean> {
        return settingsDataStore.isDarkMode()
    }

    override suspend fun setDarkMode(isDark: Boolean) {
        settingsDataStore.setDarkMode(isDark)
    }

    override fun getLanguage(): Flow<String> {
        return settingsDataStore.getLanguage()
    }

    override suspend fun setLanguage(languageCode: String) {
        settingsDataStore.setLanguage(languageCode)
    }
}