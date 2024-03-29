package com.silverorange.videoplayer.presentation.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.presentation.ui.components.VideoDescription
import com.silverorange.videoplayer.presentation.ui.components.VideoPlayer
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
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(150.dp),
                )
            }
        } else {
            if (state.videos.isNotEmpty()) {
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
        } else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No videos found",
                    style = MaterialTheme.typography.h5.copy(
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

