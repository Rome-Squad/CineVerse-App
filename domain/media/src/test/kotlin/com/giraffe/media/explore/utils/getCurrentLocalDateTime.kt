package com.giraffe.media.explore.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getCurrentLocalDateTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())