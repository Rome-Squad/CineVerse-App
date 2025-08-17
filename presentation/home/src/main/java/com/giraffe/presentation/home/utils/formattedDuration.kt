package com.giraffe.presentation.home.utils

fun Int?.formatDuration(): String =
    this?.takeIf { it > 0 }?.let { totalMinutes ->
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        buildString {
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m")
        }.trim()
    } ?: ""