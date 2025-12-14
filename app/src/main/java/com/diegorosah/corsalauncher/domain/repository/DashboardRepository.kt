package com.diegorosah.corsalauncher.domain.repository

import com.diegorosah.corsalauncher.domain.model.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun observeConnectivityStatus(): Flow<ConnectivityStatus>
}
