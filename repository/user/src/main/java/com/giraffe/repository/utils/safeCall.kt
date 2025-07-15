package com.giraffe.repository.utils

suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapToMoviesException(e)
    }
}

fun mapToMoviesException(e: Throwable): Throwable {
    return UnknownError(e.message)

}