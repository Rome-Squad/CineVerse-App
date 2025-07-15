package com.giraffe.media.series.exceptions

abstract class NetworkException: Exception()

class NoInternetNetworkException: NetworkException()
class RequestTimeoutNetworkException: NetworkException()
class TooManyRequestsNetworkException: NetworkException()
class ServerNetworkException: NetworkException()
class SerializationNetworkException: NetworkException()
class RedirectException: NetworkException()
class ClientException: NetworkException()
class InvalidRequestMethodException: NetworkException()
class InvalidRequestException: NetworkException()
class UnknownNetworkException : NetworkException()
