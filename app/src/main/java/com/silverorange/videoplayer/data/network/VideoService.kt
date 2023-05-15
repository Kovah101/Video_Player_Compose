package com.silverorange.videoplayer.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverorange.videoplayer.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface VideoService {

    companion object{
        private val TAG = VideoService::class.java.simpleName
        private const val HEADER_ACCEPT = "Accept"

        private val httpClient = OkHttpClient.Builder().apply {
            addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader(HEADER_ACCEPT, "application/json")
                        .build()
                )
            }
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            addInterceptor(HttpInterceptor()) }
            .build()

        @OptIn(ExperimentalSerializationApi::class)
        val videoService: VideoService by lazy {
            val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.VIDEO_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
               // .addConverterFactory(NonStrictJsonSerializer.serializer.asConverterFactory("application/json".toMediaType()))
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

    class HttpInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request: Request = chain.request()
            val response: okhttp3.Response = chain.proceed(request)
            val responseBody: ResponseBody? = response.body
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            return response
        }
    }

    @GET("videos/")
    suspend fun getVideos(): Response<List<VideoDto>>
}