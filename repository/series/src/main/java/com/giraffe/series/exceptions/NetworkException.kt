package com.giraffe.series.exceptions

open class NetworkExceptios: Exception()

class NoInternetNetworkException: NetworkExceptios()
class RequestTimeoutNetworkException: NetworkExceptios()
class TooManyRequestsNetworkException: NetworkExceptios()
class ServerNetworkException: NetworkExceptios()
class SerializationNetworkException: NetworkExceptios()
class RedirectException: NetworkExceptios()
class ClientException: NetworkExceptios()
class InvalidRequestMethodException: NetworkExceptios()
class InvalidRequestException: NetworkExceptios()