package com.giraffe.match.utils

fun formatDuration(minutes: Int?): String {
    if (minutes == null || minutes <= 0) return ""
    val hours = minutes / 60
    val mins = minutes % 60
    return buildString {
        if (hours > 0) append("${hours}h ")
        if (mins > 0) append("${mins}m")
    }.trim()
}
