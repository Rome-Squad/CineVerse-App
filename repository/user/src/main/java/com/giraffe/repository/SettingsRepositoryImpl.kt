package com.giraffe.repository

import com.giraffe.repository.datasource.local.SettingsLocalDataSource
import com.giraffe.repository.utils.safeCall
import com.giraffe.repository.utils.safeFlow
import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun isDarkMode(): Flow<Boolean> = safeFlow { localDataSource.isDarkMode() }

    override suspend fun setDarkMode(isDark: Boolean) =
        safeCall { localDataSource.setDarkMode(isDark) }

    override fun getLanguage(): Flow<String> = safeFlow { localDataSource.getLanguage() }

    override suspend fun setLanguage(languageCode: String) =
        safeCall { localDataSource.setLanguage(languageCode) }

    override fun getContentPreference(): Flow<ContentPreference> =
        safeFlow {
            localDataSource.getContentPreference()
                .map { preferenceName -> ContentPreference.valueOf(preferenceName) }
        }

    override suspend fun setContentPreference(preference: ContentPreference) =
        safeCall { localDataSource.setContentPreference(preference.name) }
}