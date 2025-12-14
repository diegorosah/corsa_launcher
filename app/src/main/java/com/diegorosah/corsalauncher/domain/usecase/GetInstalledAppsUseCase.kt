package com.diegorosah.corsalauncher.domain.usecase

import com.diegorosah.corsalauncher.domain.model.AppInfo
import com.diegorosah.corsalauncher.domain.repository.AppRepository

class GetInstalledAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): List<AppInfo> {
        return repository.getInstalledApps()
    }
}
