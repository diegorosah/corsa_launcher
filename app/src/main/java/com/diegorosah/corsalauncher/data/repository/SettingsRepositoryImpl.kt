package com.diegorosah.corsalauncher.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.diegorosah.corsalauncher.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    private val NAV_APP_KEY = stringPreferencesKey("nav_app_package")

    override fun getPreferredNavigationApp(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[NAV_APP_KEY] ?: "com.waze" // Default to Waze
        }
    }

    override suspend fun setPreferredNavigationApp(packageName: String) {
        context.dataStore.edit { preferences ->
            preferences[NAV_APP_KEY] = packageName
        }
    }
}
