package com.giraffe.explore.util

import kotlinx.coroutines.delay

suspend inline fun <T> retryIO(
    times: Int = 3,
    initialDelay: Long = 500L,
    maxDelay: Long = 2000L,
    factor: Double = 2.0,
    crossinline block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
    }
    return block()
}