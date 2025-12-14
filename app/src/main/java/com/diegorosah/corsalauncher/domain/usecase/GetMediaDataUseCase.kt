package com.diegorosah.corsalauncher.domain.usecase

import com.diegorosah.corsalauncher.domain.model.MediaData
import com.diegorosah.corsalauncher.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow

class GetMediaDataUseCase(private val repository: MediaRepository) {
    operator fun invoke(): Flow<MediaData> {
        return repository.observeMediaData()
    }
}
