package com.giraffe.user.repository

import com.giraffe.user.entity.ContentPreference
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun isDarkMode(): Flow<Boolean>
    suspend fun setDarkMode(isDark: Boolean)
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(languageCode: String)
    fun getContentPreference(): Flow<ContentPreference>
    suspend fun setContentPreference(preference: ContentPreference)
}