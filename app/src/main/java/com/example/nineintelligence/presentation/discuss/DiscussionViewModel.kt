package com.example.nineintelligence.presentation.discuss

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.util.EventLogger

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class DiscussionViewModel(
    val player: ExoPlayer
) : ViewModel() {
    var exoPlayer: ExoPlayer? = null
        private set

    init {
        exoPlayer = player
        player.addAnalyticsListener(EventLogger())
    }

    fun reInitExoPlayer() {
        exoPlayer = player
    }

    fun nullifyExoPlayer() {
        exoPlayer = null
    }

    fun playSelectedVideo(item: String?, context: Context) {
        val defaultSourceFactory = DefaultDataSource.Factory(context)
        val dataSourceFactory = DefaultDataSource.Factory(
            context, defaultSourceFactory
        )
        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(item ?: ""))
        player.apply {
            setMediaSource(source)
            prepare()
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}