package com.diegorosah.corsalauncher.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegorosah.corsalauncher.domain.model.NavigationData
import com.diegorosah.corsalauncher.domain.usecase.GetNavigationDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    getNavigationDataUseCase: GetNavigationDataUseCase
) : ViewModel() {

    val navigationData: StateFlow<NavigationData> = getNavigationDataUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NavigationData()
        )
}
