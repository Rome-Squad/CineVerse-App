package com.giraffe.series.exceptions


open class NetworkException: Exception()

class NoInternetNetworkException: NetworkException()
class RequestTimeoutNetworkException: NetworkException()
class TooManyRequestsNetworkException: NetworkException()
class ServerNetworkException: NetworkException()
class RedirectException : NetworkException()
class ClientException : NetworkException()

