package com.silverorange.videoplayer.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val data: List<VideoDto>
)

@Serializable
data class VideoDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("hlsURL") val hlsURL: String,
    @SerialName("fullURL") val fullURL: String,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("author") val author: AuthorDto
)

@Serializable
data class AuthorDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
