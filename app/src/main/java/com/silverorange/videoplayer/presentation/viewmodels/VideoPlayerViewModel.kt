package com.silverorange.videoplayer.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.data.models.Author
import com.silverorange.videoplayer.data.models.VideoData
import com.silverorange.videoplayer.domain.VideoRepository
import com.silverorange.videoplayer.presentation.events.VideoPlayerEvents
import com.silverorange.videoplayer.presentation.states.VideoPlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel(), VideoPlayerEvents {

    companion object {
        private val TAG = VideoPlayerViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(VideoPlayerState.empty())
    val state = _state.asStateFlow()

    init {
        retrieveVideoList()
    }

    override fun nextVideo() {
        if (_state.value.selectedVideoIndex < _state.value.videos.size - 1) {
            _state.update {
                it.copy(
                    selectedVideoIndex = it.selectedVideoIndex + 1
                )
            }
        }
    }

    override fun previousVideo() {
        if (_state.value.selectedVideoIndex > 0) {
            _state.update {
                it.copy(
                    selectedVideoIndex = it.selectedVideoIndex - 1
                )
            }
        }
    }

    private fun retrieveVideoList() {
        viewModelScope.launch {
            videoRepository.getVideoList().onSuccess {
                sortVideosByDate(it)
            }.onFailure {
                Log.e(TAG, "error in retrieveVideoList: ", it)
            }
        }
    }

    private fun sortVideosByDate(videos: List<VideoData>) {
        _state.update {
            it.copy(
                videos = videos.sortedByDescending { video -> video.publishedAt },
                selectedVideoIndex = 0,
                isLoading = false
            )
        }
    }

}