package com.giraffe.user.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnboardingDatastore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setFirstTimeStatus() {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(IS_FIRST_TIME)] = false
        }
    }

    suspend fun isFirstTime(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(IS_FIRST_TIME)] != false
        }.first()
    }

    companion object {
        private const val IS_FIRST_TIME = "IS_FIRST_TIME"
    }
}