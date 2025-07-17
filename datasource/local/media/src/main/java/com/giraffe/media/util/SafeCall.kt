package com.giraffe.media.util

import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteException
import com.giraffe.media.exception.CorruptDatabaseException
import com.giraffe.media.exception.DiskAccessException
import com.giraffe.media.exception.InvalidIdException
import com.giraffe.media.exception.MediaException
import com.giraffe.media.exception.UnknownNetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.sql.SQLException

suspend fun <T> safeCall(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        throw mapToMediaException(e)
    }
}

fun <T> safeFlow(block: () -> Flow<T>): Flow<T> {
    return flow {
        emitAll(block())
    }.catch { e ->
        throw mapToMediaException(e)
    }
}



private fun mapToMediaException(throwable: Throwable): MediaException = when (throwable) {

    is SQLiteException,
    is SQLException,
    is CursorIndexOutOfBoundsException,
    is android.database.StaleDataException -> CorruptDatabaseException()

    is IOException -> DiskAccessException()

    is IllegalArgumentException,
    is IllegalStateException -> InvalidIdException()

    is NullPointerException -> CorruptDatabaseException()

    else -> UnknownNetworkException()
}