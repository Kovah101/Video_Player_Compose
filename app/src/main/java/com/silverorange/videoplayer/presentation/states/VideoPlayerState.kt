package com.silverorange.videoplayer.presentation.states

import com.silverorange.videoplayer.data.models.VideoData

data class VideoPlayerState(
    val videos: List<VideoData>,
    val isLoading : Boolean,
    val selectedVideoIndex : Int
) {
    companion object {
        fun empty() = VideoPlayerState(
            videos = emptyList(),
            isLoading = true,
            selectedVideoIndex = 0
        )
    }
}
