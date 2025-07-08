package com.giraffe.series

open class NetworkRequestException(message: String = "", statusCode: Int = 0) : Exception(message)
class InvalidRequestException : NetworkRequestException()
class InvalidRequestMethodException : NetworkRequestException()
