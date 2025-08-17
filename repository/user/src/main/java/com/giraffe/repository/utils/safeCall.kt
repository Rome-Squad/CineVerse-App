package com.giraffe.repository.utils

import com.giraffe.repository.exceptions.ApiDataException
import com.giraffe.repository.exceptions.CorruptDatabaseDataException
import com.giraffe.repository.exceptions.CreateGuestSessionException
import com.giraffe.repository.exceptions.DiskAccessDataException
import com.giraffe.repository.exceptions.InvalidCredentialsException
import com.giraffe.repository.exceptions.InvalidIdDataException
import com.giraffe.repository.exceptions.NoInternetDataException
import com.giraffe.repository.exceptions.NotFoundDataException
import com.giraffe.repository.exceptions.RequestTimeoutDataException
import com.giraffe.repository.exceptions.SerializationDataException
import com.giraffe.repository.exceptions.SessionCreationException
import com.giraffe.repository.exceptions.SessionNotFoundException
import com.giraffe.repository.exceptions.TokenCreationException
import com.giraffe.repository.exceptions.TokenValidationException
import com.giraffe.repository.exceptions.UnknownNetworkDataException
import com.giraffe.user.exception.AccessDeniedException
import com.giraffe.user.exception.GuestSessionException
import com.giraffe.user.exception.InvalidLoginException
import com.giraffe.user.exception.InvalidUsernameMatchException
import com.giraffe.user.exception.InvalidUsernameOrPasswordException
import com.giraffe.user.exception.NoInternetException
import com.giraffe.user.exception.UnknownException
import com.giraffe.user.exception.UserException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

suspend fun <T> safeCall(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        throw mapToDomainException(e)
    }
}

fun <T> safeFlow(block: () -> Flow<T>): Flow<T> {
    return block().catch { e ->
        throw mapToDomainException(e)
    }
}

fun mapToDomainException(e: Throwable): UserException = when (e) {
    is ApiDataException -> when (e.code) {
        401, 403 -> InvalidUsernameOrPasswordException()
        404 -> InvalidUsernameMatchException()
        400, 422 -> InvalidLoginException()
        else -> UnknownException()
    }

    is InvalidCredentialsException -> InvalidUsernameOrPasswordException()
    is TokenCreationException,
    is TokenValidationException,
    is SessionCreationException -> InvalidLoginException()

    is SessionNotFoundException -> InvalidUsernameMatchException()
    is InvalidIdDataException -> InvalidLoginException()
    is NotFoundDataException -> InvalidUsernameMatchException()
    is RequestTimeoutDataException,
    is NoInternetDataException,
    is UnknownNetworkDataException -> NoInternetException()

    is SerializationDataException -> UnknownException()
    is CorruptDatabaseDataException,
    is DiskAccessDataException -> AccessDeniedException()

    is CreateGuestSessionException -> GuestSessionException()

    else -> UnknownException()
}
