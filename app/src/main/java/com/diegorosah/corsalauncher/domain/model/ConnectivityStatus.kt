package com.diegorosah.corsalauncher.domain.model

data class ConnectivityStatus(
    val isWifiEnabled: Boolean = false,
    val isBluetoothEnabled: Boolean = false,
    val isGpsEnabled: Boolean = false,
    val batteryLevel: Int = 0, // 0-100
    val isCharging: Boolean = false,
    val batteryTemperature: Float = 0f // Celsius
)
