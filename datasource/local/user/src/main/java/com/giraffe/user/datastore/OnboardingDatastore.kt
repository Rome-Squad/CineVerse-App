package com.giraffe.user.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnboardingDatastore @Inject constructor(
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    suspend fun setFirstTimeStatus() {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(IS_FIRST_TIME)] = false
        }
    }

    suspend fun isFirstTime(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(IS_FIRST_TIME)] != false
        }.first()
    }

    companion object {
        private const val DATA_STORE_NAME = "CineVerseOnBoardingDataStore"
        private const val IS_FIRST_TIME = "IS_FIRST_TIME"
    }
}