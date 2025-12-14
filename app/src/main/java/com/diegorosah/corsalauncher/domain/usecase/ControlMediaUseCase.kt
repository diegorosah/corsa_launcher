package com.diegorosah.corsalauncher.domain.usecase

import com.diegorosah.corsalauncher.domain.repository.MediaRepository

class ControlMediaUseCase(private val repository: MediaRepository) {
    
    fun playPause() = repository.playPause()
    fun skipToNext() = repository.skipToNext()
    fun skipToPrevious() = repository.skipToPrevious()
}
