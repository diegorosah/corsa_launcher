package com.diegorosah.corsalauncher.domain.model

import android.graphics.Bitmap

data class MediaData(
    val title: String = "No Media",
    val artist: String = "",
    val album: String = "",
    val artwork: Bitmap? = null,
    val isPlaying: Boolean = false,
    val packageName: String = ""
)
