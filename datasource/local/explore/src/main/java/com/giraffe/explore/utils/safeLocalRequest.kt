package com.giraffe.explore.utils

import com.giraffe.explore.exceptions.NotFoundException
import com.giraffe.explore.exceptions.UnKnownLocalDatabaseException
import com.giraffe.explore.exceptions.ValidationException


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