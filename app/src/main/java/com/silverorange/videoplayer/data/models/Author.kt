package com.silverorange.videoplayer.data.models

data class Author(
    val id: String,
    val name: String
) {
    companion object {
        fun empty() = Author(
            id = "",
            name = ""
        )
    }
}
