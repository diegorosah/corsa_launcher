package com.diegorosah.corsalauncher.data.service

import android.content.ComponentName
import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.service.notification.NotificationListenerService
import android.util.Log
import com.diegorosah.corsalauncher.domain.model.MediaData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MediaControllerService : NotificationListenerService() {

    companion object {
        private val _mediaData = MutableStateFlow(MediaData())
        val mediaData: StateFlow<MediaData> = _mediaData.asStateFlow()
        
        private var activeController: MediaController? = null

        fun playPause() {
            activeController?.let { controller ->
                val state = controller.playbackState?.state
                if (state == PlaybackState.STATE_PLAYING) {
                    controller.transportControls.pause()
                } else {
                    controller.transportControls.play()
                }
            }
        }

        fun skipToNext() {
            activeController?.transportControls?.skipToNext()
        }

        fun skipToPrevious() {
            activeController?.transportControls?.skipToPrevious()
        }
    }

    private lateinit var mediaSessionManager: MediaSessionManager

    override fun onCreate() {
        super.onCreate()
        mediaSessionManager = getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
        
        try {
            val componentName = ComponentName(this, MediaControllerService::class.java)
            mediaSessionManager.addOnActiveSessionsChangedListener(
                { controllers -> processControllers(controllers) },
                componentName
            )
            
            // Initial check
            val controllers = mediaSessionManager.getActiveSessions(componentName)
            processControllers(controllers)
        } catch (e: SecurityException) {
            Log.e("MediaService", "Permission missing: BIND_NOTIFICATION_LISTENER_SERVICE", e)
        }
    }

    private fun processControllers(controllers: List<MediaController>?) {
        if (controllers.isNullOrEmpty()) return

        // Pick the first active controller or keep the current one if valid
        val controller = controllers.firstOrNull() ?: return
        
        if (activeController?.packageName != controller.packageName) {
            activeController = controller
            registerCallback(controller)
            updateMediaData(controller)
        }
    }

    private fun registerCallback(controller: MediaController) {
        controller.registerCallback(object : MediaController.Callback() {
            override fun onPlaybackStateChanged(state: PlaybackState?) {
                updateMediaData(controller)
            }

            override fun onMetadataChanged(metadata: MediaMetadata?) {
                updateMediaData(controller)
            }
        })
    }

    private fun updateMediaData(controller: MediaController) {
        val metadata = controller.metadata
        val playbackState = controller.playbackState
        
        val title = metadata?.getString(MediaMetadata.METADATA_KEY_TITLE) ?: "Unknown Title"
        val artist = metadata?.getString(MediaMetadata.METADATA_KEY_ARTIST) ?: "Unknown Artist"
        val album = metadata?.getString(MediaMetadata.METADATA_KEY_ALBUM) ?: ""
        val artwork = metadata?.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
        val isPlaying = playbackState?.state == PlaybackState.STATE_PLAYING

        _mediaData.update {
            it.copy(
                title = title,
                artist = artist,
                album = album,
                artwork = artwork,
                isPlaying = isPlaying,
                packageName = controller.packageName
            )
        }
    }
}
