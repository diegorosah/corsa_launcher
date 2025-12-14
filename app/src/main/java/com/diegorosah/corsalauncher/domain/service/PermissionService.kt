package com.diegorosah.corsalauncher.domain.service

interface PermissionService {
    fun hasNotificationAccess(): Boolean
    fun hasLocationAccess(): Boolean
}
