package com.giraffe.media.exception

open class MediaException : Exception()

class NoInternetException : MediaException()
class RequestTimeoutException : MediaException()
class TooManyRequestsException : MediaException()
class ServerException : MediaException()
class RedirectedException : MediaException()
class ClientErrorException : MediaException()
class SerializationException : MediaException()
class ApiException(val code: Int) : MediaException()

class InvalidApiKeyException : MediaException()
class UnauthorizedAccessException : MediaException()
class ForbiddenAccessException : MediaException()
class RateLimitExceededException : MediaException()

class NotFoundException : MediaException()
class InvalidIdException : MediaException()
class CorruptDatabaseException : MediaException()
class DiskAccessException : MediaException()

class UnknownNetworkException : MediaException()