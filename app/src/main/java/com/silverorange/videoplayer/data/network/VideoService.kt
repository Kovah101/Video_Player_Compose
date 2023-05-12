package com.silverorange.videoplayer.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverorange.videoplayer.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface VideoService {

    companion object{
        private val TAG = VideoService::class.java.simpleName

        private val httpClient = OkHttpClient.Builder().build()

        @OptIn(ExperimentalSerializationApi::class)
        val videoService: VideoService by lazy {
            val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.VIDEO_API_URL)
                .addConverterFactory(NonStrictJsonSerializer.serializer.asConverterFactory("application/json".toMediaType()))
                .client(httpClient)
                .build()

            builder.create(VideoService::class.java)
        }
    }

    object NonStrictJsonSerializer {
        val serializer = Json {
            isLenient = true
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
        }
    }

    @GET("/")
    suspend fun getVideos(): Response<VideoResponse>
}