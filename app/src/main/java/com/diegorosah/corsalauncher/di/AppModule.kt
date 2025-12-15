package com.diegorosah.corsalauncher.di

import android.content.Context
import com.diegorosah.corsalauncher.data.repository.*
import com.diegorosah.corsalauncher.data.service.PermissionServiceImpl
import com.diegorosah.corsalauncher.domain.repository.*
import com.diegorosah.corsalauncher.domain.service.PermissionService
import com.diegorosah.corsalauncher.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppRepository(@ApplicationContext context: Context): AppRepository {
        return AppRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideNavigationRepository(@ApplicationContext context: Context): NavigationRepository {
        return NavigationRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(@ApplicationContext context: Context): MediaRepository {
        return MediaRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(@ApplicationContext context: Context): DashboardRepository {
        return DashboardRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providePermissionService(@ApplicationContext context: Context): PermissionService {
        return PermissionServiceImpl(context)
    }

    @Provides
    @Singleton
    fun provideGetInstalledAppsUseCase(repository: AppRepository): GetInstalledAppsUseCase {
        return GetInstalledAppsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLaunchAppUseCase(repository: AppRepository): LaunchAppUseCase {
        return LaunchAppUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNavigationDataUseCase(repository: NavigationRepository): GetNavigationDataUseCase {
        return GetNavigationDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideControlMediaUseCase(repository: MediaRepository): ControlMediaUseCase {
        return ControlMediaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMediaDataUseCase(repository: MediaRepository): GetMediaDataUseCase {
        return GetMediaDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetConnectivityStatusUseCase(repository: DashboardRepository): GetConnectivityStatusUseCase {
        return GetConnectivityStatusUseCase(repository)
    }
}
