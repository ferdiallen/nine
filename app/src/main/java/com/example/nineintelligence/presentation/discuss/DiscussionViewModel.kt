package com.example.nineintelligence.presentation.discuss

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player

class DiscussionViewModel(
    val player: Player
) : ViewModel() {
    init {
        player.apply {
            setMediaItem(
                MediaItem.fromUri(
                    "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4"
                )
            )
            prepare()
        }
    }

    fun playSelectedVideo(item: Uri?) {

    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}