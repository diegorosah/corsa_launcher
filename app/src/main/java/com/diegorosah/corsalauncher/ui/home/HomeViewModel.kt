package com.diegorosah.corsalauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegorosah.corsalauncher.domain.model.AppInfo
import com.diegorosah.corsalauncher.domain.service.PermissionService
import com.diegorosah.corsalauncher.domain.usecase.GetInstalledAppsUseCase
import com.diegorosah.corsalauncher.domain.usecase.LaunchAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val launchAppUseCase: LaunchAppUseCase,
    private val permissionService: PermissionService
) : ViewModel() {

    private val _allApps = MutableStateFlow<List<AppInfo>>(emptyList())
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _displayedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val displayedApps: StateFlow<List<AppInfo>> = _displayedApps.asStateFlow()

    private val _missingPermissions = MutableStateFlow<List<String>>(emptyList())
    val missingPermissions: StateFlow<List<String>> = _missingPermissions.asStateFlow()

    init {
        loadApps()
        checkPermissions()
    }

    fun checkPermissions() {
        val missing = mutableListOf<String>()
        if (!permissionService.hasNotificationAccess()) {
            missing.add("Notification Access")
        }
        if (!permissionService.hasLocationAccess()) {
            missing.add("Location Access")
        }
        _missingPermissions.value = missing
    }

    private fun loadApps() {
        viewModelScope.launch {
            val appsList = getInstalledAppsUseCase()
            _allApps.value = appsList
            _displayedApps.value = appsList
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _displayedApps.value = _allApps.value
        } else {
            _displayedApps.value = _allApps.value.filter { 
                it.name.contains(query, ignoreCase = true) 
            }
        }
    }

    fun launchApp(packageName: String) {
        launchAppUseCase(packageName)
    }
}
