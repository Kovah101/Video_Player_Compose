package com.silverorange.videoplayer.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.presentation.viewmodels.VideoPlayerViewModel

@Composable
fun VideoPlayerScreen(
    videoPlayerViewModel: VideoPlayerViewModel = viewModel()
) {
    val state = videoPlayerViewModel.state.collectAsState().value

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
        Column {
            Text(text = "Hello world!")
        }

    }

}