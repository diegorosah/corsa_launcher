package com.diegorosah.corsalauncher.domain.repository

import com.diegorosah.corsalauncher.domain.model.AppInfo

interface AppRepository {
    suspend fun getInstalledApps(): List<AppInfo>
    fun launchApp(packageName: String)
}
