package com.giraffe.series.exception

open class SeriesException: Exception()

class NotFoundError : SeriesException()
class NetworkError : SeriesException()
class InvalidApiKey : SeriesException()
class ServerError : SeriesException()
class UnknownError : SeriesException()
