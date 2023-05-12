package com.silverorange.videoplayer.data.models

data class VideoData(
    val id: String,
    val title: String,
    val description: String,
    val hlsUrl: String,
    val fullUrl: String,
    val publishedAt: String,
    val author: Author
) {
    companion object {
        fun empty() = VideoData(
            id = "",
            title = "",
            description = "",
            hlsUrl = "",
            fullUrl = "",
            publishedAt = "",
            author = Author.empty()
        )
    }
}
