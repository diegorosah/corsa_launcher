package com.diegorosah.corsalauncher.domain.usecase

import com.diegorosah.corsalauncher.domain.model.NavigationData
import com.diegorosah.corsalauncher.domain.repository.NavigationRepository
import kotlinx.coroutines.flow.Flow

class GetNavigationDataUseCase(private val repository: NavigationRepository) {
    operator fun invoke(): Flow<NavigationData> {
        return repository.observeNavigationData()
    }
}
