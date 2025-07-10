package com.giraffe.series.exception

import java.rmi.ServerException

open class SeriesException: Exception()

//network exceptions
class NoInternetException : SeriesException()
class ServerException : SeriesException()
class TimeoutException :SeriesException()
class RedirectedException:SeriesException()
class ClientErrorException:SeriesException()

//local exceptions
class NotFoundElementException :SeriesException()
class ValidationExceptions : SeriesException()
class CorruptDatabaseException:SeriesException()
class DiskAccessException:SeriesException()

//Unknown exceptions
class UnknownException : SeriesException()
