package com.silverorange.videoplayer.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silverorange.videoplayer.R

@Composable
fun VideoControls(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit
) {
    val isControlsVisible = remember(isVisible()) { isVisible() }

    var play by remember { mutableStateOf(false) }

    AnimatedVisibility(
        modifier = modifier,
        visible = isControlsVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            VideoControl(
                size = 60.dp,
                iconSize = 40.dp,
                onClick = onPreviousClick,
                icon = R.drawable.previous,
                description = "Previous video"
            )

            VideoControl(
                size = 80.dp,
                iconSize = 60.dp,
                onClick = { onPauseToggle()
                    play = !play },
                icon = if (play) {
                    R.drawable.pause
                } else {
                    R.drawable.play
                },
                description = "Play/Pause"
            )

            VideoControl(
                size = 60.dp,
                iconSize = 40.dp,
                onClick = onNextClick,
                icon = R.drawable.next ,
                description = "Next video"
            )
        }
    }
}

@Composable
fun VideoControl(
    size: Dp,
    iconSize: Dp,
    onClick: () -> Unit,
    icon: Int,
    description: String
) {
    IconButton(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(colorResource(id = R.color.white))
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.black),
                shape = CircleShape
            ),
        onClick = onClick
    ) {
        Image(
            modifier = Modifier.size(iconSize),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = icon),
            contentDescription = description
        )
    }
}


