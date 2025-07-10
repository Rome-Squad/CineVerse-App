package com.giraffe.series.utils

import com.giraffe.series.exception.CorruptDatabaseException
import com.giraffe.series.exception.DiskAccessException
import com.giraffe.series.exception.NotFoundException
import com.giraffe.series.exception.UnknownLocalDatabaseException
import com.giraffe.series.exception.ValidationException
import java.io.IOException


suspend inline fun <T> safeLocalCall(
    block: suspend () -> T
): Resource<T> {
    return try {
        val result = block()

        //  handle null or empty manually
        if (result is Collection<*> && result.isEmpty()) {
            Resource.Empty
        } else {
            Resource.Success(result)
        }

    } catch (e: Exception) {
        Resource.Error(mapToLocalDatabaseException(e))
    }
}

fun mapToLocalDatabaseException(e: Throwable): Throwable {
    return when (e) {
        is NoSuchElementException -> NotFoundException()
        is IllegalArgumentException -> ValidationException()
        is IllegalStateException -> CorruptDatabaseException()
        is IOException -> DiskAccessException()
        else -> UnknownLocalDatabaseException()
    }
}