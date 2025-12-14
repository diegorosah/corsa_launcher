package com.diegorosah.corsalauncher.ui.media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegorosah.corsalauncher.domain.model.MediaData
import com.diegorosah.corsalauncher.domain.usecase.ControlMediaUseCase
import com.diegorosah.corsalauncher.domain.usecase.GetMediaDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    getMediaDataUseCase: GetMediaDataUseCase,
    private val controlMediaUseCase: ControlMediaUseCase
) : ViewModel() {

    val mediaData: StateFlow<MediaData> = getMediaDataUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MediaData()
        )

    fun onPlayPauseClick() {
        controlMediaUseCase.playPause()
    }

    fun onNextClick() {
        controlMediaUseCase.skipToNext()
    }

    fun onPreviousClick() {
        controlMediaUseCase.skipToPrevious()
    }
}
