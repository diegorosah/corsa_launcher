package com.diegorosah.corsalauncher.data.repository

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.diegorosah.corsalauncher.domain.model.AppInfo
import com.diegorosah.corsalauncher.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepositoryImpl(private val context: Context) : AppRepository {

    override suspend fun getInstalledApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val apps = packageManager.queryIntentActivities(intent, 0)
        
        apps.mapNotNull { resolveInfo ->
            val activityInfo = resolveInfo.activityInfo
            val packageName = activityInfo.packageName
            
            if (packageName == context.packageName) return@mapNotNull null

            AppInfo(
                name = resolveInfo.loadLabel(packageManager).toString(),
                packageName = packageName,
                icon = resolveInfo.loadIcon(packageManager)
            )
        }.sortedBy { it.name }
    }

    override fun launchApp(packageName: String) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
