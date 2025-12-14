package com.diegorosah.corsalauncher.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegorosah.corsalauncher.domain.model.ConnectivityStatus
import com.diegorosah.corsalauncher.domain.usecase.GetConnectivityStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getConnectivityStatusUseCase: GetConnectivityStatusUseCase
) : ViewModel() {

    val connectivityStatus: StateFlow<ConnectivityStatus> = getConnectivityStatusUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ConnectivityStatus()
        )
}
