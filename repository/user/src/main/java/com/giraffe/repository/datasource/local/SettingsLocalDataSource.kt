package com.giraffe.repository.datasource.local

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    fun isDarkMode(): Flow<Boolean>
    suspend fun setDarkMode(isDark: Boolean)
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(languageCode: String)
    fun getContentPreference(): Flow<String>
    suspend fun setContentPreference(preferenceName: String)
}