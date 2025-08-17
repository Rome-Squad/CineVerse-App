package com.giraffe.presentation.home.utils

fun Int?.formatDuration(): String {
    if (this == null || this <= 0) return ""
    val hours = this / 60
    val minutes = this % 60
    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m")
    }.trim()
}
