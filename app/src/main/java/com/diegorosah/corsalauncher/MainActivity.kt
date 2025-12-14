package com.diegorosah.corsalauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.diegorosah.corsalauncher.ui.dashboard.DashboardScreen
import com.diegorosah.corsalauncher.ui.dashboard.DashboardViewModel
import com.diegorosah.corsalauncher.ui.home.HomeScreen
import com.diegorosah.corsalauncher.ui.home.HomeViewModel
import com.diegorosah.corsalauncher.ui.media.MediaViewModel
import com.diegorosah.corsalauncher.ui.navigation.NavigationViewModel
import com.diegorosah.corsalauncher.ui.settings.SettingsScreen
import com.diegorosah.corsalauncher.ui.settings.SettingsViewModel
import com.diegorosah.corsalauncher.ui.theme.CorsaLauncherTheme
import dagger.hilt.android.AndroidEntryPoint

enum class Screen {
    HOME, SETTINGS, DASHBOARD
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            CorsaLauncherTheme {
                var currentScreen by remember { mutableStateOf(Screen.HOME) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        Screen.HOME -> {
                            val homeViewModel: HomeViewModel = hiltViewModel()
                            val navigationViewModel: NavigationViewModel = hiltViewModel()
                            val mediaViewModel: MediaViewModel = hiltViewModel()
                            
                            HomeScreen(
                                homeViewModel = homeViewModel,
                                navigationViewModel = navigationViewModel,
                                mediaViewModel = mediaViewModel,
                                onSettingsClick = { currentScreen = Screen.SETTINGS },
                                onDashboardClick = { currentScreen = Screen.DASHBOARD }
                            )
                        }
                        Screen.SETTINGS -> {
                            val settingsViewModel: SettingsViewModel = hiltViewModel()
                            SettingsScreen(
                                viewModel = settingsViewModel,
                                onBackClick = { currentScreen = Screen.HOME }
                            )
                        }
                        Screen.DASHBOARD -> {
                            val dashboardViewModel: DashboardViewModel = hiltViewModel()
                            val navigationViewModel: NavigationViewModel = hiltViewModel()
                            val mediaViewModel: MediaViewModel = hiltViewModel()
                            
                            DashboardScreen(
                                dashboardViewModel = dashboardViewModel,
                                navigationViewModel = navigationViewModel,
                                mediaViewModel = mediaViewModel,
                                onBackClick = { currentScreen = Screen.HOME }
                            )
                        }
                    }
                }
            }
        }
    }
}
