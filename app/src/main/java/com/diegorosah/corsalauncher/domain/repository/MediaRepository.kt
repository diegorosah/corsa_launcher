package com.diegorosah.corsalauncher.domain.repository

import com.diegorosah.corsalauncher.domain.model.MediaData
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun observeMediaData(): Flow<MediaData>
    fun playPause()
    fun skipToNext()
    fun skipToPrevious()
}
