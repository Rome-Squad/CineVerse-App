package com.giraffe.movie.exceptions

open class NetworkException: Exception()

class NoInternetNetworkException: NetworkException()
class RequestTimeoutNetworkException: NetworkException()
class TooManyRequestsNetworkException: NetworkException()
class ServerNetworkException: NetworkException()
class SerializationNetworkException: NetworkException()
class UnknownNetworkException: NetworkException()
