package com.giraffe.series

open class NetworkRequestException(message: String = "", statusCode: Int = 0) : Exception(message)

class ApiKeyNotFoundException : NetworkRequestException()

class NoInternetException : NetworkRequestException()

class InvalidRequestException : NetworkRequestException()

class InvalidRequestMethodException : NetworkRequestException()

class ServerException : NetworkRequestException()

class ClientException : NetworkRequestException()

class UnknownException : NetworkRequestException()