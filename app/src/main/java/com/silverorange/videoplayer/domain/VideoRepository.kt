package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.data.models.VideoData

interface VideoRepository {
    suspend fun getVideoList(): List<VideoData>
}