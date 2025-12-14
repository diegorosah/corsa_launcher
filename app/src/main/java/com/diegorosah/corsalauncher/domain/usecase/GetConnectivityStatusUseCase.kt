package com.diegorosah.corsalauncher.domain.usecase

import com.diegorosah.corsalauncher.domain.model.ConnectivityStatus
import com.diegorosah.corsalauncher.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow

class GetConnectivityStatusUseCase(private val repository: DashboardRepository) {
    operator fun invoke(): Flow<ConnectivityStatus> {
        return repository.observeConnectivityStatus()
    }
}
