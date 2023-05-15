package com.silverorange.videoplayer.presentation.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.presentation.viewmodels.VideoPlayerViewModel

@Composable
fun VideoPlayerScreen(
    videoPlayerViewModel: VideoPlayerViewModel = viewModel()
) {
    val state = videoPlayerViewModel.state.collectAsState().value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Video Player",
                        style = MaterialTheme.typography.h5.copy(
                            color = colorResource(id = R.color.white),
                            textAlign = TextAlign.Center
                        )
                    )
                },
                backgroundColor = colorResource(id = R.color.black),
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp,
            )
        }
    ) {
        if (state.isLoading) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "No videos found",
                style = MaterialTheme.typography.h5.copy(
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center
                )
            )
        } else {
            Column {

                VideoPlayer(
                    videoUrl = state.videos[state.selectedVideoIndex].hlsUrl,
                    context = context,
                    previousClicked = { videoPlayerViewModel.previousVideo() },
                    nextClicked = { videoPlayerViewModel.nextVideo() }
                )

                VideoDescription(
                    title = state.videos[state.selectedVideoIndex].title,
                    author = state.videos[state.selectedVideoIndex].author.name,
                    description = state.videos[state.selectedVideoIndex].description
                )
            }
        }

    }

}

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
                .build()
            exoPlayer.setMediaItem(videoItem)
            exoPlayer.prepare()
        }

    var isPlaying by remember { mutableStateOf(false) }
    Log.d("VideoTest", "VideoPlayer: isPlaying: $isPlaying")

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)) {

        DisposableEffect(
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.Center),
                factory = {
                    StyledPlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                    }
                })
        ) {
            onDispose { exoPlayer.release() }
        }


        VideoControls(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.Center),
            isPlaying =  isPlaying ,
            onPreviousClick = { previousClicked() },
            onPauseToggle = {
                            if (exoPlayer.isPlaying) {
                                exoPlayer.pause()
                            } else {
                                exoPlayer.play()
                            }
                isPlaying = isPlaying.not()
            },
        onNextClick = { nextClicked() }
        )
    }

}

@Composable
fun VideoDescription(
    title: String,
    author: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5.copy(
                color = colorResource(id = R.color.black),
            )
        )
        Text(
            text = author,
            style = MaterialTheme.typography.h6.copy(
                color = colorResource(id = R.color.black),
            )
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body1.copy(
                color = colorResource(id = R.color.black),
            )
        )
    }
}

@Composable
fun VideoControls(
    modifier: Modifier = Modifier,
    isPlaying:  Boolean,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit
) {
    //val isVideoPlaying = remember(isPlaying()) { isPlaying() }
    Log.d("VideoTest", "VideoControls: isPlaying: $isPlaying")

    Row(modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

            IconButton(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.white))
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.black),
                        shape = CircleShape),
                onClick = onPreviousClick,
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.previous),
                    contentDescription = "Previous video"
                )
            }

        IconButton(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.white))
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.black),
                    shape = CircleShape),
            onClick = onPauseToggle) {
            Image(
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop,
                painter =
                if (isPlaying) {
                    painterResource(id = R.drawable.pause)
                } else {
                    painterResource(id = R.drawable.play)
                },
                contentDescription = "Play/Pause"
            )
        }

        IconButton(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.white))
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.black),
                    shape = CircleShape),
            onClick = onNextClick
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.next),
                contentDescription = "Next Video"
            )
        }
    }
}

