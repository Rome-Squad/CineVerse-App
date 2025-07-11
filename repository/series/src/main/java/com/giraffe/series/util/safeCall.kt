package com.giraffe.series.utils

suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        e.printStackTrace()
        throw e
    }
}
