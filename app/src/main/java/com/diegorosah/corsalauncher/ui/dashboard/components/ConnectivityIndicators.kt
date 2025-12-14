package com.diegorosah.corsalauncher.ui.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothDisabled
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.GpsOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.diegorosah.corsalauncher.domain.model.ConnectivityStatus

@Composable
fun ConnectivityIndicators(
    status: ConnectivityStatus,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusIcon(
            isActive = status.isWifiEnabled,
            activeIcon = Icons.Default.Wifi,
            inactiveIcon = Icons.Default.WifiOff
        )
        
        StatusIcon(
            isActive = status.isBluetoothEnabled,
            activeIcon = Icons.Default.Bluetooth,
            inactiveIcon = Icons.Default.BluetoothDisabled
        )
        
        StatusIcon(
            isActive = status.isGpsEnabled,
            activeIcon = Icons.Default.GpsFixed,
            inactiveIcon = Icons.Default.GpsOff
        )

        // Battery
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.BatteryFull,
                contentDescription = "Battery",
                tint = if (status.isCharging) Color.Green else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${status.batteryLevel}%",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun StatusIcon(
    isActive: Boolean,
    activeIcon: ImageVector,
    inactiveIcon: ImageVector
) {
    Icon(
        imageVector = if (isActive) activeIcon else inactiveIcon,
        contentDescription = null,
        tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    )
}
