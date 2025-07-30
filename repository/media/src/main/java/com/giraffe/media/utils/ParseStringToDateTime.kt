package com.giraffe.media.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun String?.toLocalDateTime(): LocalDateTime? {
    if (this.isNullOrBlank()) {
        return null
    }

    return try {
        Instant.parse(this).toLocalDateTime(TimeZone.UTC)
    } catch (e: Exception) {
        null
    }
}