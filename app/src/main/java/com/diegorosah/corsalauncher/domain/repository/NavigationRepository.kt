package com.diegorosah.corsalauncher.domain.repository

import com.diegorosah.corsalauncher.domain.model.NavigationData
import kotlinx.coroutines.flow.Flow

interface NavigationRepository {
    fun observeNavigationData(): Flow<NavigationData>
}
