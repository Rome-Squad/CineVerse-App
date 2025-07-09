package com.giraffe.explore.exceptions

open class NetworkException: Exception()

class NoInternetException: NetworkException()
class RequestTimeoutException: NetworkException()
class TooManyRequestsException: NetworkException()
class ServerException: NetworkException()
class SerializationException: NetworkException()

class UnknownRemoteException: NetworkException()