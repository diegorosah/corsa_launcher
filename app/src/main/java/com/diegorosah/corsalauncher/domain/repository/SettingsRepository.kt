package com.diegorosah.corsalauncher.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getPreferredNavigationApp(): Flow<String>
    suspend fun setPreferredNavigationApp(packageName: String)
}
