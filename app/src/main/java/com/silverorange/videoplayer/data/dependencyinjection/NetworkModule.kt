package com.silverorange.videoplayer.data.dependencyinjection

import com.silverorange.videoplayer.data.network.VideoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideVideoService() = VideoService.videoService
}