package com.giraffe.repository.exceptions

open class UserDataException : Exception()

class InvalidCredentialsException : UserDataException()
class TokenCreationException : UserDataException()
class TokenValidationException : UserDataException()
class SessionCreationException : UserDataException()
class SessionNotFoundException : UserDataException()
class SerializationDataException : UserDataException()
class RequestTimeoutDataException : UserDataException()
class NoInternetDataException : UserDataException()
class InvalidIdDataException : UserDataException()

class NotFoundDataException : UserDataException()

class CorruptDatabaseDataException : UserDataException()

class DiskAccessDataException : UserDataException()
class UnknownNetworkDataException : UserDataException()

class CreateGuestSessionException : UserDataException()

class ApiDataException(val code: Int) : UserDataException()


