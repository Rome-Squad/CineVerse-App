package com.giraffe.media.util

import android.database.CursorIndexOutOfBoundsException
import android.database.StaleDataException
import android.database.sqlite.SQLiteException
import com.giraffe.media.exception.CorruptDatabaseDataException
import com.giraffe.media.exception.DiskAccessDataException
import com.giraffe.media.exception.InvalidIdDataException
import com.giraffe.media.exception.MediaDataException
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.exception.UnknownNetworkDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
    return block().catch { e ->
        throw mapToMediaException(e)
    }
}


private fun mapToMediaException(throwable: Throwable): MediaDataException = when (throwable) {

    is SQLiteException,
    is SQLException,
    is CursorIndexOutOfBoundsException,
    is StaleDataException -> CorruptDatabaseDataException()

    is IOException -> DiskAccessDataException()
    is NoInternetDataException -> NoInternetDataException()
    is IllegalArgumentException,
    is IllegalStateException -> InvalidIdDataException()

    is NullPointerException -> CorruptDatabaseDataException()

    else -> UnknownNetworkDataException()
}