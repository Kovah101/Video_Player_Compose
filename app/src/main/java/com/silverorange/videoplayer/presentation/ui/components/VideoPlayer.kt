package com.silverorange.videoplayer.presentation.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPlayer(
    videoUrl: String,
    context: Context,
    previousClicked: () -> Unit,
    nextClicked: () -> Unit
) {
    Log.d("VideoTest", "VideoPlayerUrl: $videoUrl")

    val exoPlayer = ExoPlayer.Builder(context)
        .build()
        .also { exoPlayer ->
            val videoItem = MediaItem.Builder()
                .setUri(videoUrl)
                .setMediaId("Url = $videoUrl")
                .build()
            Log.d("VideoTest", "VideoItem url: ${videoItem.mediaId} ")
            exoPlayer.addMediaItem(videoItem)
            exoPlayer.prepare()
        }

    var showControls by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        DisposableEffect(key1 = Unit) {
            onDispose { exoPlayer.release() }
        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.Center)
                .clickable {
                    showControls = showControls.not()
                },
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                }
            })


        VideoControls(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.Center),
            isVisible = { showControls },
            onPreviousClick = { previousClicked() },
            onPauseToggle = {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
            },
            onNextClick = { nextClicked() }
        )
    }
}