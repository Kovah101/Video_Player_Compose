package com.silverorange.videoplayer.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.RichTextStyle
import com.silverorange.videoplayer.R

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
        RichText(
            style = RichTextStyle()
        ) {
            Markdown(content = description)
        }
    }
}