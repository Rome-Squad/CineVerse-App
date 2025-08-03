package com.giraffe.user.util

import android.database.CursorIndexOutOfBoundsException
import android.database.StaleDataException
import android.database.sqlite.SQLiteException
import com.giraffe.repository.exceptions.CorruptDatabaseDataException
import com.giraffe.repository.exceptions.DiskAccessDataException
import com.giraffe.repository.exceptions.InvalidIdDataException
import com.giraffe.repository.exceptions.NoInternetDataException
import com.giraffe.repository.exceptions.UnknownNetworkDataException
import com.giraffe.repository.exceptions.UserDataException
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
        throw mapToUserException(e)
    }
}

fun <T> safeFlow(block: () -> Flow<T>): Flow<T> {
    return flow {
        emitAll(block())
    }.catch { e ->
        throw mapToUserException(e)
    }
}


private fun mapToUserException(throwable: Throwable): UserDataException = when (throwable) {

    is SQLiteException,
    is SQLException,
    is CursorIndexOutOfBoundsException,
    is StaleDataException -> CorruptDatabaseDataException()

    is IOException -> DiskAccessDataException()
    is NoInternetDataException -> UserDataException()
    is IllegalArgumentException,
    is IllegalStateException -> InvalidIdDataException()

    is NullPointerException -> CorruptDatabaseDataException()

    else -> UnknownNetworkDataException()
}