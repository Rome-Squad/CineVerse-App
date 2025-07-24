package com.giraffe.repository.exceptions

open class AuthDataException: Exception()

class InvalidCredentialsException : AuthDataException()
class TokenCreationException : AuthDataException()
class TokenValidationException : AuthDataException()
class SessionCreationException : AuthDataException()
class SerializationDataException : AuthDataException()
class RequestTimeoutDataException : AuthDataException()
class NoInternetDataException : AuthDataException()
class InvalidIdDataException : AuthDataException()
class UnknownNetworkDataException : AuthDataException()

class ApiDataException(val code: Int) : AuthDataException()


