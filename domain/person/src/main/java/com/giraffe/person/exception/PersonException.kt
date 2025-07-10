package com.giraffe.person.exception

abstract class PersonException(message: String = "") : Exception(message)
class PersonNotFoundException : PersonException()
class InvalidPersonIdException : PersonException()
class InvalidPersonNameException : PersonException()
class NetworkException : PersonException()
class UnknownException : PersonException()

// API-related
class InvalidApiKeyException : PersonException()
class RateLimitExceededException : PersonException()
class UnauthorizedAccessException : PersonException()
class ForbiddenAccessException : PersonException()

// Network-related
class ServerErrorException : PersonException()
