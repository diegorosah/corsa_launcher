package com.diegorosah.corsalauncher.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diegorosah.corsalauncher.R
import com.diegorosah.corsalauncher.ui.dashboard.components.ConnectivityIndicators
import com.diegorosah.corsalauncher.ui.dashboard.components.Speedometer
import com.diegorosah.corsalauncher.ui.dashboard.components.TemperatureIndicator
import com.diegorosah.corsalauncher.ui.media.MediaViewModel
import com.diegorosah.corsalauncher.ui.media.MediaWidget
import com.diegorosah.corsalauncher.ui.navigation.NavigationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel,
    navigationViewModel: NavigationViewModel,
    mediaViewModel: MediaViewModel,
    onBackClick: () -> Unit
) {
    val connectivityStatus by dashboardViewModel.connectivityStatus.collectAsState()
    val navigationData by navigationViewModel.navigationData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dashboard)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    ConnectivityIndicators(status = connectivityStatus)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Speedometer (Centerpiece)
            Speedometer(
                currentSpeed = navigationData.currentSpeed,
                modifier = Modifier
                    .size(300.dp)
                    .padding(vertical = 32.dp)
            )

            TemperatureIndicator(
                temperatureCelsius = connectivityStatus.batteryTemperature,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Media Widget at bottom
            MediaWidget(
                viewModel = mediaViewModel,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
