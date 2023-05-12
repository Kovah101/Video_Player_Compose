package com.silverorange.videoplayer.data.implementations

import android.util.Log
import com.silverorange.videoplayer.data.models.Author
import com.silverorange.videoplayer.data.models.VideoData
import com.silverorange.videoplayer.data.network.AuthorDto
import com.silverorange.videoplayer.data.network.VideoDto
import com.silverorange.videoplayer.data.network.VideoService
import com.silverorange.videoplayer.domain.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor (
    private val videoService: VideoService
): VideoRepository{

    companion object {
        private val TAG = VideoRepositoryImpl::class.java.simpleName
    }

    override suspend fun getVideoList(): Result<List<VideoData>> {
        val response = videoService.getVideos()
        return if (response.isSuccessful) {
            Result.success(response.body()?.data?.map { it.toModel() } ?: emptyList())
        } else {
            Log.e(TAG, "getVideoList error ${response.errorBody()}", )
            Result.failure(Throwable(response.message()))
        }
    }

    private fun VideoDto.toModel() = VideoData(
        id = id,
        title = title,
        description = description,
        hlsUrl = hlsUrl,
        fullUrl = fullUrl,
        publishedAt = publishedAt,
        author = author.toModel()
    )

    private fun AuthorDto.toModel() = Author(
        id = id,
        name = name
    )

}