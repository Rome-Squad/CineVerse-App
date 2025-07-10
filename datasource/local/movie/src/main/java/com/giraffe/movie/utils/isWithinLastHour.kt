package com.giraffe.movie.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun LocalDateTime.isWithinLastHour(): Boolean {

    val nowInstant = getCurrentLocalDateTime().toInstant(TimeZone.currentSystemDefault())
    val thresholdInstant = nowInstant - 1.hours

    return this.toInstant(TimeZone.currentSystemDefault()) < thresholdInstant
}