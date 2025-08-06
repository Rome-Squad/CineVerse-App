package com.giraffe.user.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun isDarkMode(): Flow<Boolean>
    suspend fun setDarkMode(isDark: Boolean)
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(languageCode: String)
}