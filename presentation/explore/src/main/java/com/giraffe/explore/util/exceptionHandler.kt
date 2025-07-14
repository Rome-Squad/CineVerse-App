package com.giraffe.explore.util

import androidx.annotation.StringRes
import com.giraffe.explore.R
import com.giraffe.series.exception.NoInternetException as NoInternetExceptionSeries
import com.giraffe.series.exception.TimeoutException as TimeoutExceptionSeries
import com.giraffe.series.exception.RedirectedException as RedirectedExceptionSeries
import com.giraffe.series.exception.ClientErrorException as ClientErrorExceptionSeries
import com.giraffe.series.exception.ServerException as ServerExceptionSeries
import com.giraffe.series.exception.SerializationException as SerializationExceptionSeries
import com.giraffe.series.exception.NotFoundElementException as NotFoundElementExceptionSeries
import com.giraffe.series.exception.ValidationExceptions as ValidationExceptionsSeries
import com.giraffe.series.exception.CorruptDatabaseException as CorruptDatabaseExceptionSeries
import com.giraffe.series.exception.DiskAccessException as DiskAccessExceptionSeries
import com.giraffe.series.exception.UnknownException as UnknownExceptionSeries
import com.giraffe.explore.exceptions.NotFoundException as NotFoundExceptionExplore
import com.giraffe.explore.exceptions.UnrecognizableDataException as UnrecognizableDataExceptionExplore
import com.giraffe.explore.exceptions.NoInternetException as NoInternetExceptionExplore
import com.giraffe.explore.exceptions.ValidationException as ValidationExceptionsExplore
import com.giraffe.explore.exceptions.ServerException as ServerExceptionExplore
import com.giraffe.explore.exceptions.UnknownException as UnknownExceptionExplore
import com.giraffe.explore.exceptions.RequestTimeoutException as RequestTimeoutExceptionExplore
import com.giraffe.explore.exceptions.TooManyRequestsException as TooManyRequestsExceptionExplore
import com.giraffe.movies.exception.NetworkError as NetworkErrorMovies
import com.giraffe.movies.exception.InvalidApiKey as InvalidApiKeyMovies
import com.giraffe.movies.exception.NotFoundError as NotFoundErrorMovies
import com.giraffe.movies.exception.ServerError as ServerErrorMovies
import com.giraffe.movies.exception.UnknownError as UnknownErrorMovies

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


fun <T> exceptionHandler(
    state: MutableStateFlow<T>
): CoroutineExceptionHandler where T : HasErrorMessage<T> {
    return CoroutineExceptionHandler { _, throwable ->
        val resId = mapExceptionToStringRes(throwable)
        state.update { it.withErrorMessage(resId) }
    }
}


@StringRes
fun mapExceptionToStringRes(throwable: Throwable): Int {
    return when (throwable) {
        // Explore
        is NoInternetExceptionExplore -> R.string.error_explore_no_internet
        is ValidationExceptionsExplore -> R.string.error_explore_validation
        is ServerExceptionExplore -> R.string.error_explore_server
        is UnknownExceptionExplore -> R.string.error_explore_unknown
        is RequestTimeoutExceptionExplore -> R.string.error_explore_timeout
        is TooManyRequestsExceptionExplore -> R.string.error_explore_too_many
        is UnrecognizableDataExceptionExplore -> R.string.error_explore_unrecognized
        is NotFoundExceptionExplore -> R.string.error_explore_not_found

        // Series
        is NoInternetExceptionSeries -> R.string.error_series_no_internet
        is TimeoutExceptionSeries -> R.string.error_series_timeout
        is RedirectedExceptionSeries -> R.string.error_series_redirect
        is ClientErrorExceptionSeries -> R.string.error_series_client
        is ServerExceptionSeries -> R.string.error_series_server
        is SerializationExceptionSeries -> R.string.error_series_serialization
        is NotFoundElementExceptionSeries -> R.string.error_series_not_found
        is ValidationExceptionsSeries -> R.string.error_series_validation
        is CorruptDatabaseExceptionSeries -> R.string.error_series_corrupt_db
        is DiskAccessExceptionSeries -> R.string.error_series_disk
        is UnknownExceptionSeries -> R.string.error_series_unknown

        // Movies
        is NetworkErrorMovies -> R.string.error_movies_network
        is InvalidApiKeyMovies -> R.string.error_movies_invalid_key
        is NotFoundErrorMovies -> R.string.error_movies_not_found
        is ServerErrorMovies -> R.string.error_movies_server
        is UnknownErrorMovies -> R.string.error_movies_unknown

        // Default fallback
        else -> R.string.error_default
    }
}
