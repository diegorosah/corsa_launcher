package com.diegorosah.corsalauncher.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diegorosah.corsalauncher.domain.model.AppInfo
import com.diegorosah.corsalauncher.domain.service.PermissionService
import com.diegorosah.corsalauncher.domain.usecase.GetInstalledAppsUseCase
import com.diegorosah.corsalauncher.domain.usecase.LaunchAppUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var getInstalledAppsUseCase: GetInstalledAppsUseCase

    @MockK
    lateinit var launchAppUseCase: LaunchAppUseCase

    @MockK
    lateinit var permissionService: PermissionService

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadApps should populate displayedApps with all apps initially`() = runTest {
        // Given
        val apps = listOf(
            AppInfo("Spotify", "com.spotify.music", null),
            AppInfo("Waze", "com.waze", null),
            AppInfo("Settings", "com.android.settings", null)
        )
        coEvery { getInstalledAppsUseCase() } returns apps
        coEvery { permissionService.hasNotificationAccess() } returns true
        coEvery { permissionService.hasLocationAccess() } returns true

        // When
        viewModel = HomeViewModel(getInstalledAppsUseCase, launchAppUseCase, permissionService)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(apps, viewModel.displayedApps.value)
    }

    @Test
    fun `onSearchQueryChanged should filter apps based on query`() = runTest {
        // Given
        val apps = listOf(
            AppInfo("Spotify", "com.spotify.music", null),
            AppInfo("Waze", "com.waze", null),
            AppInfo("Settings", "com.android.settings", null)
        )
        coEvery { getInstalledAppsUseCase() } returns apps
        coEvery { permissionService.hasNotificationAccess() } returns true
        coEvery { permissionService.hasLocationAccess() } returns true

        viewModel = HomeViewModel(getInstalledAppsUseCase, launchAppUseCase, permissionService)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.onSearchQueryChanged("Waze")

        // Then
        val expected = listOf(AppInfo("Waze", "com.waze", null))
        assertEquals(expected, viewModel.displayedApps.value)
    }

    @Test
    fun `onSearchQueryChanged with empty query should show all apps`() = runTest {
        // Given
        val apps = listOf(
            AppInfo("Spotify", "com.spotify.music", null),
            AppInfo("Waze", "com.waze", null)
        )
        coEvery { getInstalledAppsUseCase() } returns apps
        coEvery { permissionService.hasNotificationAccess() } returns true
        coEvery { permissionService.hasLocationAccess() } returns true

        viewModel = HomeViewModel(getInstalledAppsUseCase, launchAppUseCase, permissionService)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.onSearchQueryChanged("Spo")
        viewModel.onSearchQueryChanged("")

        // Then
        assertEquals(apps, viewModel.displayedApps.value)
    }

    @Test
    fun `checkPermissions should update missingPermissions list`() = runTest {
        // Given
        coEvery { getInstalledAppsUseCase() } returns emptyList()
        coEvery { permissionService.hasNotificationAccess() } returns false
        coEvery { permissionService.hasLocationAccess() } returns true

        // When
        viewModel = HomeViewModel(getInstalledAppsUseCase, launchAppUseCase, permissionService)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val expected = listOf("Notification Access")
        assertEquals(expected, viewModel.missingPermissions.value)
    }
}
