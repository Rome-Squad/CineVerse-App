package com.giraffe.user

import com.giraffe.repository.datasource.local.SettingsLocalDataSource
import com.giraffe.user.datastore.SettingsDataStore
import com.giraffe.user.util.safeCall
import com.giraffe.user.util.safeFlow
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsLocalDataSourceImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsLocalDataSource {

    override fun isDarkMode(): Flow<Boolean> = safeFlow { settingsDataStore.isDarkMode() }

    override suspend fun setDarkMode(isDark: Boolean) = safeCall {
        settingsDataStore.setDarkMode(isDark)
    }

    override fun getLanguage(): Flow<String> = safeFlow { settingsDataStore.getLanguage() }

    override suspend fun setLanguage(languageCode: String) = safeCall {
        settingsDataStore.setLanguage(languageCode)
    }

    override fun getContentPreference(): Flow<String> =
        safeFlow { settingsDataStore.getContentPreference() }

    override suspend fun setContentPreference(preferenceName: String) = safeCall {
        settingsDataStore.setContentPreference(preferenceName)
    }
}