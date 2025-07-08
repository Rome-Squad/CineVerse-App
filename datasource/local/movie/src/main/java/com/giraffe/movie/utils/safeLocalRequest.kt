package com.giraffe.movie.utils

import com.giraffe.movie.exceptions.NotFoundException
import com.giraffe.movie.exceptions.UnKnownLocalDatabaseException
import com.giraffe.movie.exceptions.ValidationException


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
        is NoSuchElementException -> NotFoundException()
        is IllegalArgumentException -> ValidationException()
        else -> UnKnownLocalDatabaseException()
    }
}