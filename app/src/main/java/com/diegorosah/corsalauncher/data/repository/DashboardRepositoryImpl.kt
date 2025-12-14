package com.diegorosah.corsalauncher.data.repository

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import com.diegorosah.corsalauncher.domain.model.ConnectivityStatus
import com.diegorosah.corsalauncher.domain.repository.DashboardRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DashboardRepositoryImpl(
    private val context: Context
) : DashboardRepository {

    override fun observeConnectivityStatus(): Flow<ConnectivityStatus> = callbackFlow {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                trySend(getCurrentStatus())
            }
        }

        val filter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
            addAction(Intent.ACTION_BATTERY_CHANGED)
        }

        context.registerReceiver(receiver, filter)
        
        // Emit initial value
        trySend(getCurrentStatus())

        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }

    private fun getCurrentStatus(): ConnectivityStatus {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

        // WiFi / Network
        val activeNetwork = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(activeNetwork)
        val isWifi = caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

        // Bluetooth
        val isBt = bluetoothAdapter?.isEnabled == true

        // GPS
        val isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // Battery
        val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val batteryPct = if (level != -1 && scale != -1) (level * 100 / scale.toFloat()).toInt() else 0
        
        val status = batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || 
                        status == BatteryManager.BATTERY_STATUS_FULL

        val temp = batteryIntent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
        val tempCelsius = temp / 10f

        return ConnectivityStatus(
            isWifiEnabled = isWifi,
            isBluetoothEnabled = isBt,
            isGpsEnabled = isGps,
            batteryLevel = batteryPct,
            isCharging = isCharging,
            batteryTemperature = tempCelsius
        )
    }
}
