package com.silverorange.videoplayer.data.dependencyinjection

import com.silverorange.videoplayer.data.implementations.VideoRepositoryImpl
import com.silverorange.videoplayer.domain.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoRepository

}