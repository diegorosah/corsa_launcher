package com.diegorosah.corsalauncher.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.diegorosah.corsalauncher.domain.model.NavigationData
import com.diegorosah.corsalauncher.domain.model.NavigationSource
import com.diegorosah.corsalauncher.domain.repository.NavigationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationRepositoryImpl(
    private val context: Context
) : NavigationRepository {

    private val _navigationData = MutableStateFlow(NavigationData())
    
    private val wazeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                // Waze broadcasts usually contain these extras
                // Note: Actual keys might vary, these are based on common Waze integrations
                val speed = it.getIntExtra("Speed", 0) // usually in mph or km/h depending on locale, often raw value
                val limit = it.getIntExtra("SpeedLimit", 0)
                val distance = it.getIntExtra("Dist", -1)
                
                // Assuming Waze sends data, we are "navigating" or at least driving
                _navigationData.update { current ->
                    current.copy(
                        currentSpeed = speed,
                        speedLimit = limit,
                        distanceToNextCamera = distance,
                        source = NavigationSource.WAZE,
                        isNavigating = true
                    )
                }
            }
        }
    }

    init {
        registerReceivers()
    }

    private fun registerReceivers() {
        val filter = IntentFilter("com.waze.speedcam")
        // Add other actions if necessary, e.g. Google Maps if they have a public broadcast
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(wazeReceiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(wazeReceiver, filter)
        }
    }

    override fun observeNavigationData(): Flow<NavigationData> {
        return _navigationData.asStateFlow()
    }
    
    fun cleanup() {
        try {
            context.unregisterReceiver(wazeReceiver)
        } catch (e: Exception) {
            Log.e("NavigationRepo", "Error unregistering receiver", e)
        }
    }
}
