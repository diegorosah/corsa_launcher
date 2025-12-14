package com.diegorosah.corsalauncher.domain.model

enum class NavigationSource {
    WAZE,
    GOOGLE_MAPS,
    UNKNOWN
}

data class NavigationData(
    val currentSpeed: Int = 0,           // km/h
    val speedLimit: Int = 0,             // km/h
    val distanceToNextCamera: Int = -1,  // meters, -1 if none
    val source: NavigationSource = NavigationSource.UNKNOWN,
    val isNavigating: Boolean = false
)
