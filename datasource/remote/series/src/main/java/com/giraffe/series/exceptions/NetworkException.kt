package com.giraffe.series.utils

open class NetworkException: Exception()
class RedirectException() : NetworkException()
class ClientErrorException() : NetworkException()
class ApiException() : NetworkException()
class UnknownException() : NetworkException()
class ServerErrorException() : NetworkException()
