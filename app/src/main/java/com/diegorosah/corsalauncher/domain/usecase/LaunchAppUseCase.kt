package com.diegorosah.corsalauncher.domain.usecase

import com.diegorosah.corsalauncher.domain.repository.AppRepository

class LaunchAppUseCase(private val repository: AppRepository) {
    operator fun invoke(packageName: String) {
        repository.launchApp(packageName)
    }
}
