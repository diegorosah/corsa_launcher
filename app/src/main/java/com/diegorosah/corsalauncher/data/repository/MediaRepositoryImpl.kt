package com.diegorosah.corsalauncher.data.repository

import com.diegorosah.corsalauncher.data.service.MediaControllerService
import com.diegorosah.corsalauncher.domain.model.MediaData
import com.diegorosah.corsalauncher.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow

class MediaRepositoryImpl : MediaRepository {

    override fun observeMediaData(): Flow<MediaData> {
        return MediaControllerService.mediaData
    }

    override fun playPause() {
        MediaControllerService.playPause()
    }

    override fun skipToNext() {
        MediaControllerService.skipToNext()
    }

    override fun skipToPrevious() {
        MediaControllerService.skipToPrevious()
    }
}
