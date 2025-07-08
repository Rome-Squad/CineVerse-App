package com.giraffe.movie.utils

import com.giraffe.movie.exceptions.LocalDatabaseException


suspend inline fun <reified T> safeLocalRequest (
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapToLocalDatabaseException(e)
    }
}


fun mapToLocalDatabaseException(e: Throwable): Throwable {
    return when (e) {
        is NoSuchElementException -> LocalDatabaseException()
        is IllegalArgumentException -> LocalDatabaseException()
        else -> UnknownError()
    }
}