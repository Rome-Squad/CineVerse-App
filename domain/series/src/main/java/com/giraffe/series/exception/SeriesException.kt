package com.giraffe.series.exception

open class SeriesException: Exception()


class NotFoundException : SeriesException()
class NoInternetException : SeriesException()
class InvalidApiKey : SeriesException()
class ServerErrorException : SeriesException()
class UnknownException : SeriesException()
class ValidationException : SeriesException()