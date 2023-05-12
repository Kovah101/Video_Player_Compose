package com.silverorange.videoplayer.data.implementations

import com.silverorange.videoplayer.data.models.VideoData
import com.silverorange.videoplayer.domain.VideoRepository

class VideoRepositoryImpl: VideoRepository {

    companion object {
        private val TAG = VideoRepositoryImpl::class.java.simpleName
    }

    override suspend fun getVideoList(): List<VideoData> {
        TODO("Not yet implemented")
    }

}